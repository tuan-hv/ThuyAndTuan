package com.serviceorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serviceorder.controllers.ProductController;
import com.serviceorder.dto.ProductDTO;
import com.serviceorder.entities.Product;
import com.serviceorder.exceptions.GlobalExceptionHandler;
import com.serviceorder.exceptions.ResourceNotFoundException;
import com.serviceorder.repositories.ProductRepository;
import com.serviceorder.services.ProductService;
import com.serviceorder.servicesimp.ProductServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(value = ProductControllerTest.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productServiceImp;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductController productController;

    private List<ProductDTO> productList;

    private ProductDTO productDTO;

    private Product product;

    @Before
    public void init() {
        productList = Arrays.asList(
                new ProductDTO(1, "tao1", "anh1", "mota1", 1),
                new ProductDTO(2, "tao2", "anh2", "mota2", 1),
                new ProductDTO(3, "tao3", "anh3", "mota3", 0));

        productDTO = new ProductDTO(1, "Adddddd", "anh1", "mota1", 1);

        product = new Product();
        product.setProductId(1);
        product.setName("tao1");
        product.setImage("anh1");
        product.setDescription("mota1");
        product.setPrice(1);

        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .setControllerAdvice(new GlobalExceptionHandler(null))
                .addFilters(new CORSFilter())
                .build();
    }

    @Test
    @DisplayName("Test findAll()")
    public void test_get_all_product_success() throws Exception {
        when(productServiceImp.findAllProduct()).thenReturn(productList);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
        verify(productServiceImp, times(1)).findAllProduct();
        verifyNoMoreInteractions(productServiceImp);
    }

    @Test
    @DisplayName("Test findAll() fail")
    public void test_get_all_product_fail_no_content() throws Exception {
        when(productServiceImp.findAllProduct()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isNoContent());

        verify(productServiceImp, times(1)).findAllProduct();
        verifyNoMoreInteractions(productServiceImp);
    }

    @Test
    public void test_get_product_by_id_success() throws Exception {
        when(productServiceImp.getProductById(anyInt())).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId", is(1)));
        verify(productServiceImp, times(1)).getProductById(1);
        verifyNoMoreInteractions(productServiceImp);
    }

    @Test
    public void test_create_product_success() throws Exception {
        when(productServiceImp.checkExist(productDTO)).thenReturn(false);
        when(productServiceImp.createProduct(any(ProductDTO.class))).thenReturn(Optional.of(productDTO));
        
        mockMvc.perform(
                post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void test_create_product_fail_409_conflict() throws Exception {
        when(productServiceImp.checkExist(any(ProductDTO.class))).thenReturn(true);
        mockMvc.perform(
                post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    public void test_create_product_fail_400_bad_request() throws Exception {
        when(productServiceImp.checkExist(any(ProductDTO.class))).thenReturn(false);
        when(productServiceImp.createProduct(any(ProductDTO.class))).thenReturn(Optional.empty());
        mockMvc.perform(
                post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_update_product_success() throws Exception {
        when(productServiceImp.updateProduct(anyInt(), any(ProductDTO.class))).thenReturn(Optional.of(productDTO));
        mockMvc.perform(put("/api/products/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void test_update_product_fail_404_not_found() throws ResourceNotFoundException {
        when(productServiceImp.updateProduct(anyInt(), any(ProductDTO.class))).thenThrow(ResourceNotFoundException.class);

        assertThatThrownBy(() -> productServiceImp.updateProduct(anyInt(), any(ProductDTO.class))).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void test_delete_product_success() throws Exception {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        mockMvc.perform(
                delete("/api/products/{id}", product.getProductId()))
                .andExpect(status().isOk());
    }


    @Test
    public void test_delete_product_fail_404_not_found() throws Exception {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(
                delete("/api/products/{id}", productDTO.getProductId()))
                .andExpect(status().isExpectationFailed());
    }


    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

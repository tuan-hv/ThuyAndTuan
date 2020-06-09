package com.serviceorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serviceorder.controllers.ProductController;
import com.serviceorder.entities.Product;
import com.serviceorder.repositories.ProductRepository;
import com.serviceorder.services.ProductService;
import com.serviceorder.dto.ProductDTO;
import com.serviceorder.exceptions.GlobalExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProductControllerTest.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductController productController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilters(new CORSFilter())
                .build();
    }

    @Test
    @DisplayName("Test findAll()")
    public void test_get_all_product_success() throws Exception {
        List<ProductDTO> productList = Arrays.asList(
                new ProductDTO(1,"tao1","anh1","mota1",1),
                new ProductDTO(2,"tao2","anh2","mota2",1),
                new ProductDTO(3,"tao3","anh3","mota3",0));

        when(productService.findAllProduct()).thenReturn(Optional.of(productList));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].productId", is(1)))
                .andExpect(jsonPath("$[1].productId", is(2)))
                .andExpect(jsonPath("$[2].productId", is(3)));

        verify(productService, times(1)).findAllProduct();
        verifyNoMoreInteractions(productService);
    }

    @Test
    @DisplayName("Test findAll() fail")
    public void test_get_all_product_fail_no_content() throws Exception {
        when(productService.findAllProduct()).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).findAllProduct();
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void test_get_product_by_id_success() throws Exception {
        ProductDTO productDTO = new ProductDTO(1,"tao1","anh1","mota1",1);

        when(productService.getProductById(anyInt())).thenReturn(Optional.of(productDTO));

        mockMvc.perform(get("/api/products/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId", is(1)))
                .andExpect(jsonPath("$.productName", is("tao1")))
                .andExpect(jsonPath("$.image", is("anh1")))
                .andExpect(jsonPath("$.description", is("mota1")))
                .andExpect(jsonPath("$.price", is(1.0)));

        verify(productService, times(1)).getProductById(1);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void test_get_product_by_id_fail_404_not_found() throws Exception {

        when(productService.getProductById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/{id}", 100))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getProductById(100);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void test_create_product_success() throws Exception {
        ProductDTO productDTO = new ProductDTO(1,"tao1","anh1","mota1",1);

        when(productService.checkExist(productDTO)).thenReturn(false);
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(Optional.of(productDTO));


        mockMvc.perform(
                post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void test_create_product_fail_409_conflict() throws Exception {
        ProductDTO productDTO = new ProductDTO(1,"tao1","anh1","mota1",1);

        when(productService.checkExist(productDTO)).thenReturn(true);

        mockMvc.perform(
                post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDTO)))
                .andExpect(status().isConflict());

        verify(productService, times(1)).checkExist(productDTO);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void test_update_product_success() throws Exception {
        ProductDTO productDTO = new ProductDTO(1,"tao1","anh1","mota1",1);

        when(productService.getProductById(anyInt())).thenReturn(Optional.of(productDTO));
        doReturn(Optional.of(productDTO)).when(productService).updateProduct(productDTO.getProductId(), productDTO);

        mockMvc.perform(
                put("/api/products/{id}", productDTO.getProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDTO)))
                .andExpect(status().isOk());
        verify(productService, times(1)).updateProduct(productDTO.getProductId(), productDTO);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void test_update_product_fail_404_not_found() throws Exception {
        ProductDTO productDTO = new ProductDTO(1,"tao1","anh1","mota1",1);
        when(productService.getProductById(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/products/{id}", productDTO.getProductId()));

        verifyNoMoreInteractions(productService);
    }

    @Test
    public void test_delete_product_success() throws Exception {
        Product product = new Product();
        product.setProductId(1);
        product.setName("tao1");
        product.setImage("anh1");
        product.setDescription("mota1");
        product.setPrice(1);

        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        mockMvc.perform(
                delete("/api/products/{id}", product.getProductId()))
                .andExpect(status().isOk());
    }


    @Test
    public void test_delete_product_fail_404_not_found() throws Exception {
        ProductDTO productDTO = new ProductDTO(1,"tao1","anh1","mota1",1);

        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(
                delete("/api/products/{id}", productDTO.getProductId()))
                .andExpect(status().isNotFound());
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

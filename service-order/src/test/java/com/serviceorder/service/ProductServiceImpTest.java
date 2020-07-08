package com.serviceorder.service;

import com.serviceorder.converts.ProductConvert;
import com.serviceorder.dto.ProductDTO;
import com.serviceorder.entities.Product;
import com.serviceorder.exceptions.ResourceNotFoundException;
import com.serviceorder.repositories.ProductRepository;
import com.serviceorder.services.ProductService;
import com.serviceorder.servicesimp.ProductServiceImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImpTest {

    @InjectMocks
    private ProductServiceImp productService;


    @Mock
    private ProductRepository productRepository;

    private List<Product> productList;
    private Product product;
    private ProductDTO productDTO2;
    private ProductDTO productDTO;

    @Before
    public void init() {
        productList = Arrays.asList(new Product(), new Product(), new Product());

        product = new Product();
        product.setProductId(1);
        product.setName("product");
        product.setDescription("description");
        product.setImage("image");
        product.setPrice(1);
        product.setStatus(1);

        productDTO2 = new ProductDTO();

        productDTO = new ProductDTO(3, "tao3", "anh3", "mota3", 0);
    }

    @Test
    public void test_get_all_product_success() {
        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDTO> productDTOList = productService.findAllProduct();

        assertEquals(3, productDTOList.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void test_get_all_product_not_found() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        List<ProductDTO> productDTOList = productService.findAllProduct();
        Assert.assertEquals(productDTOList, Optional.empty());
    }

    @Test
    public void test_get_product_by_id_success() throws ResourceNotFoundException {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        ProductDTO productDTO = productService.getProductById(1);

        assertEquals(1, productDTO.getProductId());
        assertEquals("product", productDTO.getProductName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void test_get_product_by_id_fail_404_not_found() throws ResourceNotFoundException {
        when(productRepository.findById(anyInt())).thenThrow(ResourceNotFoundException.class);
        ProductDTO productDTO = productService.getProductById(111);
    }

    @Test
    public void test_create_product_sucess() {
        when(productRepository.save(any(Product.class))).thenAnswer((Answer<Product>) invocation -> {
            Product product1 = (Product) invocation.getArguments()[0];
            product1.setProductId(1);
            return product1;
        });

        Optional<ProductDTO> createProduct = productService.createProduct(productDTO);

        assertNotNull(createProduct.get().getProductId());

        assertEquals(1, createProduct.get().getProductId());
    }

    @Test
    public void test_create_product_throw_exception() {
        when(productRepository.save(any(Product.class))).thenThrow(Exception.class);
        Optional<ProductDTO> createProduct = productService.createProduct(productDTO2);
        assertEquals(Optional.empty(), createProduct);
    }

    @Test
    public void test_update_product_sucess() throws ResourceNotFoundException {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        Optional<ProductDTO> updateProduct = productService.updateProduct(product.getProductId(), ProductConvert.convertProductToProductDto(product));

        assertThat(updateProduct, is(notNullValue()));
    }

    @Test
    public void test_update_product_fail_404_not_found() throws ResourceNotFoundException {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        Optional<ProductDTO> updateProduct = productService.updateProduct(product.getProductId(), productDTO);

        assertEquals(updateProduct, Optional.empty());
    }

    @Test
    public void test_check_product_confict() {
        when(productRepository.findByName(anyString())).thenReturn(Optional.empty());

        Boolean check = productService.checkExist(productDTO);

        assertEquals(Boolean.FALSE, check);
    }
}

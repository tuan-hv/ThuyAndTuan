package com.serviceorder.service;
import com.serviceorder.converts.ProductConvert;
import com.serviceorder.entities.Product;
import com.serviceorder.repositories.ProductRepository;
import com.serviceorder.services.ProductService;
import com.serviceorder.dto.ProductDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void init() {
        //todo
    }

    @Test
    public void test_get_all_product_success() {
        List<Product> productList = Arrays.asList(new Product(), new Product(), new Product());

        when(productRepository.findAll()).thenReturn(productList);

        Optional<List<ProductDTO>> productDTOList = productService.findAllProduct();

        assertEquals(3, productDTOList.get().size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void test_get_all_product_not_found() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        Optional<List<ProductDTO>> productDTOList = productService.findAllProduct();
        Assert.assertEquals(productDTOList, Optional.empty());
    }

    @Test
    public void test_get_product_by_id_success() {
        Product product = new Product();
        product.setProductId(1);
        product.setName("product");
        product.setDescription("description");
        product.setImage("image");
        product.setPrice(1);
        product.setStatus(1);

        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        Optional<ProductDTO> productDTO = productService.getProductById(1);

        assertEquals(1, productDTO.get().getProductId());
        assertEquals("product", productDTO.get().getProductName());
    }

    @Test
    public void test_get_product_by_id_fail_404_not_found() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
        Optional<ProductDTO> productDTO = productService.getProductById(111);
        assertEquals(Optional.empty(), productDTO);
    }

    @Test
    public void test_create_product_sucess() {
        ProductDTO product = new ProductDTO();
        product.setProductName("product");
        product.setDescription("description");
        product.setImage("image");
        product.setPrice(1);
        product.setStatus(1);

        when(productRepository.save(any(Product.class))).thenAnswer((Answer<Product>) invocation -> {
            Product product1 = (Product) invocation.getArguments()[0];
            product1.setProductId(1);
            return product1;
        });

        assertEquals(0, product.getProductId());

        Optional<ProductDTO> createProduct = productService.createProduct(product);

        assertNotNull(createProduct.get().getProductId());

        assertEquals(1, createProduct.get().getProductId());
    }

    @Test
    public void test_create_product_throw_exception() {
        ProductDTO product = new ProductDTO();

        when(productRepository.save(any(Product.class))).thenThrow(Exception.class);
        Optional<ProductDTO> createProduct = productService.createProduct(product);
        assertEquals(Optional.empty(), createProduct);
    }

    @Test
    public void test_update_product_sucess() {
        Product product = new Product();
        product.setProductId(1);
        product.setName("product");
        product.setDescription("description");
        product.setImage("image");
        product.setPrice(1);
        product.setStatus(1);

        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        Optional<ProductDTO> updateProduct = productService.updateProduct(product.getProductId(), ProductConvert.convertProductToProductDto(product));

        assertThat(updateProduct, is(notNullValue()));
    }

    @Test
    public void test_update_product_fail_404_not_found() {
        ProductDTO product = new ProductDTO(3,"tao3","anh3","mota3",0);

        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        Optional<ProductDTO> updateProduct = productService.updateProduct(product.getProductId(), product);

        assertEquals(updateProduct, Optional.empty());
    }

    @Test
    public void test_check_product_confict() {
        ProductDTO product = new ProductDTO(3,"tao3","anh3","mota3",0);

        when(productRepository.findByName(anyString())).thenReturn(Optional.empty());

        Boolean check = productService.checkExist(product);

        assertEquals(Boolean.FALSE, check);
    }
}

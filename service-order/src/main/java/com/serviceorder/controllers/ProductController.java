package com.serviceorder.controllers;

import com.serviceorder.converts.ProductConvert;
import com.serviceorder.dto.ProductDTO;
import com.serviceorder.entities.Product;
import com.serviceorder.exceptions.FileDuplicateException;
import com.serviceorder.exceptions.ResourceNotFoundException;
import com.serviceorder.repositories.ProductRepository;
import com.serviceorder.services.ProductService;
import com.serviceorder.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @Create by: tuan den
 * @date:3/6/2020
 */


@RestController
@RequestMapping("/api")
@Validated
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        Optional<List<ProductDTO>> companyDTOS = productService.findAllProduct();
        if (companyDTOS.isPresent()) {
            return ResponseEntity.ok(companyDTOS.get());
        }
        LOGGER.info(Constant.NOT_FOUND);
        return ResponseEntity.noContent().build();
    }

    /*@Retryable(
            value = {ResourceNotFoundException.class},
            backoff = @Backoff(delay = 10000L)
    )*/
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable(value = "id") Integer productId)
            throws ResourceNotFoundException {

        LOGGER.info(Constant.FIND_BY_ID, Constant.PRODUCT, productId);
        ProductDTO productDTO = productService.getProductById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUNT + productId));
        LOGGER.info(Constant.FIND_BY_SUCCESS, Constant.PRODUCT);
        return productDTO != null ? ResponseEntity.ok().body(productDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {

        if (productService.checkExist(productDTO))
            throw new FileDuplicateException(Constant.PRODUCT + Constant.EXIST);
        LOGGER.info(Constant.START_SAVE);
        Optional<ProductDTO> createProduct = productService.createProduct(productDTO);
        return createProduct.isPresent() ? ResponseEntity.ok().body(createProduct.get()) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable(value = "id") Integer productId,
                                                    @Valid @RequestBody ProductDTO productDTO) throws ResourceNotFoundException {

        LOGGER.info(Constant.START_UPDATE);
        ProductDTO updateProduct = productService.updateProduct(productId, productDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUNT + productId));
        return updateProduct != null ? ResponseEntity.ok(updateProduct) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable(value = "id") Integer productId)
            throws ResourceNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUNT + productId));
        productRepository.delete(product);
        LOGGER.info(Constant.DELETE_SUCCESS, Constant.PRODUCT);
        return ResponseEntity.ok(ProductConvert.convertProductToProductDto(product));
    }

  /*  @Recover
    public void recover() {
        LOGGER.info("Recovering");
    }*/

}

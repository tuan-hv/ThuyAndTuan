package com.serviceorder.controllers;

import com.serviceorder.dto.ProductDTO;
import com.serviceorder.exceptions.ResourceNotFoundException;
import com.serviceorder.services.ProductService;
import com.serviceorder.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> companyDTOS = productService.findAllProduct();
        if (!companyDTOS.isEmpty()) {
            return ResponseEntity.ok(companyDTOS);
        }
        LOGGER.info(Constant.NOT_FOUND);
        return ResponseEntity.noContent().build();
    }
    @Retryable(
            value = {ResourceNotFoundException.class},
            backoff = @Backoff(delay = 10000L)
    )
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable(value = "id") Integer productId)
            throws ResourceNotFoundException {

        LOGGER.info(Constant.FIND_BY_ID, Constant.PRODUCT, productId);
        ProductDTO productDTO = productService.getProductById(productId);
        LOGGER.info(Constant.FIND_BY_SUCCESS, Constant.PRODUCT);
        return ResponseEntity.ok().body(productDTO);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        LOGGER.info(Constant.START_SAVE);
        Optional<ProductDTO> createProduct = productService.createProduct(productDTO);
        return createProduct.isPresent() ? ResponseEntity.ok().body(createProduct.get())
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable(value = "id") Integer productId,
                                                    @Valid @RequestBody ProductDTO productDTO) throws ResourceNotFoundException {

        LOGGER.info(Constant.START_UPDATE);
        Optional<ProductDTO> updateProduct = productService.updateProduct(productId, productDTO);
        return updateProduct.isPresent() ? ResponseEntity.ok(updateProduct.get())
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Map> deleteProduct(@PathVariable(value = "id") Integer productId) {
        try {
            Map map = new HashMap();
            Boolean productDelete = productService.deleteProduct(productId);
            map.put("Delete", productDelete);
            LOGGER.info(Constant.DELETE_SUCCESS, Constant.PRODUCT);
            return new ResponseEntity<>(map, HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Recover
    public void helpHere() {
        System.out.println("Recovery place!");
    }

}

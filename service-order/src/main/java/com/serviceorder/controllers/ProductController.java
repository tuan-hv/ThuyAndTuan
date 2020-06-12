package com.serviceorder.controllers;

import com.serviceorder.converts.ProductConvert;
import com.serviceorder.repositories.ProductRepository;
import com.serviceorder.entities.Product;
import com.serviceorder.services.ProductService;
import com.serviceorder.utils.Constant;
import com.serviceorder.dto.ProductDTO;
import com.serviceorder.exceptions.FileDuplicateException;
import com.serviceorder.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        Optional<List<ProductDTO>> companyDTOS = productService.findAllProduct();
        if (companyDTOS.isPresent()) {
            return ResponseEntity.ok(companyDTOS.get());
        }
        LOGGER.info("No product found!");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products/{id}")

    public ResponseEntity<ProductDTO> getProductById(@PathVariable(value = "id") Integer productId)
            throws ResourceNotFoundException {

        LOGGER.info("Find by id product :: " , productId);
        ProductDTO companyDTO = productService.getProductById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUNT + productId));
        LOGGER.info("Find by id product success!");
        return ResponseEntity.ok().body(companyDTO);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {

        if (productService.checkExist(productDTO))
            throw new FileDuplicateException("Product is already exist!");
        LOGGER.info("starting save product...");
        Optional<ProductDTO> createCompany = productService.createProduct(productDTO);
        return ResponseEntity.ok().body(createCompany.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable(value = "id") Integer productId,
                                                    @Valid @RequestBody ProductDTO productDTO) throws ResourceNotFoundException {

        LOGGER.info("starting update product...");
        ProductDTO updateCompany = productService.updateProduct(productId, productDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUNT + productId));
        return ResponseEntity.ok(updateCompany);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable(value = "id") Integer productId)
            throws ResourceNotFoundException {
        LOGGER.info("start get product by id!");
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUNT + productId));
        productRepository.delete(product);
        LOGGER.info("delete product success!");
        return ResponseEntity.ok(ProductConvert.convertProductToProductDto(product));
    }

}

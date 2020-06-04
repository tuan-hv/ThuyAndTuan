package com.serviceorder.services;

import com.serviceorder.converts.ProductConvert;
import com.serviceorder.repositories.ProductRepository;
import com.serviceorder.entities.Product;
import dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Create by: tuan den
 * @date:3/6/2020
 */

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public Optional<List<ProductDTO>> findAllProduct() {
        List<Product> productList = productRepository.findAll();
        if (productList != null) {
            LOGGER.info("get all Product success!");
            List<ProductDTO> productDTOList = new ArrayList<>();
            productList.forEach(p ->
                    productDTOList.add(ProductConvert.convertProductToProductDto(p))
            );
            return Optional.of(productDTOList);
        }
        LOGGER.info("get all product fail!");
        return Optional.empty();
    }

    public Optional<ProductDTO> getProductById(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);


        if (product.isPresent()) {
            LOGGER.info("get product by id success...");
            return Optional.of(ProductConvert.convertProductToProductDto(product.get()));
        }
        LOGGER.info("get product by id fail...");
        return Optional.empty();
    }

    public Optional<ProductDTO> createProduct(ProductDTO productDTO) {
        try {
            Product productCreate = productRepository.save(ProductConvert.convertProductDtoToProduct(productDTO));
            productDTO.setProductId(productCreate.getProductId());
            LOGGER.info("save product success!");
            return Optional.of(productDTO);
        } catch (Exception e) {
            LOGGER.error("error with save product ::", e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<ProductDTO> updateProduct(Integer productId, ProductDTO productDTO) {
        Optional<Product> productUpdate = productRepository.findById(productId);

        if (productUpdate.isPresent()) {

            productUpdate.get().setProductId(productDTO.getProductId());
            productUpdate.get().setDescription(productDTO.getDescription());
            productUpdate.get().setName(productDTO.getProductName());
            productUpdate.get().setPrice(productDTO.getPrice());
            productUpdate.get().setImage(productDTO.getImage());
            productUpdate.get().setCreatedAt(productDTO.getCreatedAt());
            productUpdate.get().setUpdatedAt(productDTO.getUpdatedAt());
            productUpdate.get().setStatus(productDTO.getStatus());

            productRepository.save(productUpdate.get());
            LOGGER.info("update product success!");
            productDTO.setProductId(productId);
            return Optional.of(productDTO);
        }
        LOGGER.info("update product fail!");
        return Optional.empty();
    }

    public Boolean checkExist(ProductDTO productDTO) {
        return productRepository.findByName(productDTO.getProductName()).isPresent();
    }

}

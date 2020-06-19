package com.serviceorder.services;

import com.serviceorder.converts.ProductConvert;
import com.serviceorder.dto.ProductDTO;
import com.serviceorder.entities.Product;
import com.serviceorder.repositories.ProductRepository;
import com.serviceorder.utils.Constant;
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

    private int retryCount = 0;

    @Autowired
    private ProductRepository productRepository;

    public Optional<List<ProductDTO>> findAllProduct() {
        List<Product> productList = productRepository.findAll();
        if (!productList.isEmpty()) {
            LOGGER.info(Constant.GET_SUCCESS, Constant.PRODUCT);
            List<ProductDTO> productDTOList = new ArrayList<>();
            productList.forEach(p ->
                    productDTOList.add(ProductConvert.convertProductToProductDto(p))
            );
            return Optional.of(productDTOList);
        }
        LOGGER.warn(Constant.GET_FAIL, Constant.PRODUCT);
        return Optional.empty();
    }

    public Optional<ProductDTO> getProductById(Integer productId) {
        LOGGER.info("Attempting at {} time(s)", ++retryCount);
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            LOGGER.info(Constant.FIND_BY_SUCCESS, Constant.PRODUCT);
            return Optional.of(ProductConvert.convertProductToProductDto(product.get()));
        }
        LOGGER.info(Constant.PRODUCT_NOT_FOUNT);
        return Optional.empty();
    }

    public Optional<ProductDTO> createProduct(ProductDTO productDTO) {
        try {
            Product productCreate = productRepository.save(ProductConvert.convertProductDtoToProduct(productDTO));
            productDTO.setProductId(productCreate.getProductId());
            LOGGER.info(Constant.SAVE_SUCCESS, Constant.PRODUCT);
            return Optional.of(productDTO);
        } catch (Exception e) {
            LOGGER.error(Constant.SAVE_FAIL, Constant.PRODUCT);
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
            LOGGER.info(Constant.UPDATE_SUCCESS, Constant.PRODUCT);
            productDTO.setProductId(productId);
            return Optional.of(productDTO);
        }
        LOGGER.info(Constant.UPDATE_FAIL, Constant.PRODUCT);
        return Optional.empty();
    }

    public Boolean checkExist(ProductDTO productDTO) {
        return productRepository.findByName(productDTO.getProductName()).isPresent();
    }

}

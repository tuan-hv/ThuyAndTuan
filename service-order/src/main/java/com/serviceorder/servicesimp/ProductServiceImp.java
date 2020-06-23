package com.serviceorder.servicesimp;

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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Create by: tuan den
 * @date:3/6/2020
 */

@Service
public class ProductServiceImp implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImp.class);

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> findAllProduct() {
        List<Product> productList = productRepository.findAll();
        if (!productList.isEmpty()) {
            LOGGER.info(Constant.GET_SUCCESS, Constant.PRODUCT);
            List<ProductDTO> productDTOList = new ArrayList<>();
            productList.forEach(p ->
                    productDTOList.add(ProductConvert.convertProductToProductDto(p))
            );
            return productDTOList;
        }
        LOGGER.warn(Constant.GET_FAIL, Constant.PRODUCT);
        return new ArrayList<>();
    }

    public ProductDTO getProductById(Integer productId) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUNT + productId));
        LOGGER.info(Constant.FIND_BY_SUCCESS, Constant.PRODUCT);
        return ProductConvert.convertProductToProductDto(product);
    }

    public Optional<ProductDTO> createProduct(ProductDTO productDTO) {
        if (checkExist(productDTO))
            throw new FileDuplicateException(Constant.PRODUCT + Constant.EXIST);
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

    public Optional<ProductDTO> updateProduct(Integer productId, ProductDTO productDTO) throws ResourceNotFoundException {
        Product productUpdate = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUNT + productId));

        try {
            productUpdate.setProductId(productDTO.getProductId());
            productUpdate.setDescription(productDTO.getDescription());
            productUpdate.setName(productDTO.getProductName());
            productUpdate.setPrice(productDTO.getPrice());
            productUpdate.setImage(productDTO.getImage());
            productUpdate.setCreatedAt(productDTO.getCreatedAt());
            productUpdate.setUpdatedAt(productDTO.getUpdatedAt());
            productUpdate.setStatus(productDTO.getStatus());

            productRepository.save(productUpdate);
            LOGGER.info(Constant.UPDATE_SUCCESS, Constant.PRODUCT);
            productDTO.setProductId(productId);
            return Optional.of(productDTO);
        } catch (Exception e) {
            LOGGER.info(Constant.UPDATE_FAIL, Constant.PRODUCT);
            return Optional.empty();
        }
    }

    public Boolean deleteProduct(Integer productId) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUNT + productId));
            productRepository.delete(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean checkExist(ProductDTO productDTO) {
        return productRepository.findByName(productDTO.getProductName()).isPresent();
    }

}

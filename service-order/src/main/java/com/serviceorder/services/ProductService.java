package com.serviceorder.services;

import com.serviceorder.dto.ProductDTO;
import com.serviceorder.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> findAllProduct();

    ProductDTO getProductById(Integer productId) throws ResourceNotFoundException;

    Optional<ProductDTO> createProduct(ProductDTO productDTO);

    Optional<ProductDTO> updateProduct(Integer productId, ProductDTO productDTO) throws ResourceNotFoundException;

    Boolean checkExist(ProductDTO productDTO);

    Boolean deleteProduct(Integer productId) throws ResourceNotFoundException;
}

package com.serviceorder.converts;

import com.serviceorder.entities.Product;
import com.serviceorder.dto.ProductDTO;

public class ProductConvert {

    private ProductConvert() {
        throw new IllegalStateException("Utility class");
    }

    public static ProductDTO convertProductToProductDto(Product product){
        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductId(product.getProductId());
        productDTO.setDescription(product.getDescription());
        productDTO.setProductName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setImage(product.getImage());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdatedAt(product.getUpdatedAt());
        productDTO.setStatus(product.getStatus());

        return productDTO;
    }

    public static Product convertProductDtoToProduct(ProductDTO productDTO){
        Product product = new Product();

        product.setProductId(productDTO.getProductId());
        product.setDescription(productDTO.getDescription());
        product.setName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setImage(productDTO.getImage());
        product.setCreatedAt(productDTO.getCreatedAt());
        product.setUpdatedAt(productDTO.getUpdatedAt());
        product.setStatus(productDTO.getStatus());

        return product;
    }
}

package com.serviceorder.dto;


import lombok.*;
import com.serviceorder.validators.PriceConstraint;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO extends AbstractDTO {
    private int productId;
    @NotBlank(message = "Product name not blank!")
    private String productName;
    private String image;
    private String description;
    @PriceConstraint
    private double price;

}

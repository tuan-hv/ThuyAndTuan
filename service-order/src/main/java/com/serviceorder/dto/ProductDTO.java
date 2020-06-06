package com.serviceorder.dto;


import lombok.*;
import com.serviceorder.validators.ContactPriceConstraint;

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
    @ContactPriceConstraint
    private double price;

}

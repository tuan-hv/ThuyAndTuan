package com.serviceorder.dto;


import com.serviceorder.validators.NameConstraint;
import lombok.*;
import com.serviceorder.validators.PriceConstraint;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO extends AbstractDTO {
    private int productId;
    @NameConstraint
    private String productName;
    @NotBlank(message = "{notnull.product.image}")
    private String image;
    @NotBlank(message = "{notnull.product.description}")
    private String description;
    @PriceConstraint
    private double price;

}

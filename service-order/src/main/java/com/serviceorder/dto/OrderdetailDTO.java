package com.serviceorder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderdetailDTO extends AbstractDTO {

    private int deltailId;
    private ProductDTO productDTO;
    private int quantity;
    private double price;
    private double amount;

    public OrderdetailDTO() {
        this.quantity = 0;
    }

    public double getAmount() {
        return this.productDTO.getPrice() * this.quantity;
    }

}

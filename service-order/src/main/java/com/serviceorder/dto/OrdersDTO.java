package com.serviceorder.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO extends AbstractDTO {
    private int ordersId;
    private double totalPrice;

    private List<OrderdetailDTO> orderDetailEntities;

    private UserDTO userDTO;
}

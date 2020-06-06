package com.serviceorder.converts;


import com.serviceorder.entities.Orders;
import com.serviceorder.dto.OrdersDTO;

public class OrderConvert {

    public static OrdersDTO convertOrdertoToOrderDTO(Orders orders){
        OrdersDTO ordersDTO = new OrdersDTO();

        ordersDTO.setOrdersId(orders.getOrderId());
        ordersDTO.setTotalPrice(orders.getTotalPrice());
        ordersDTO.setCreatedAt(orders.getCreatedAt());
        ordersDTO.setUpdatedAt(orders.getUpdatedAt());
        ordersDTO.setStatus(orders.getStatus());

        return ordersDTO;
    }

    public static Orders convertOrderDTOToOrder(OrdersDTO ordersDTO){
        Orders orders = new Orders();

        orders.setOrderId(ordersDTO.getOrdersId());
        orders.setTotalPrice(ordersDTO.getTotalPrice());
        orders.setCreatedAt(ordersDTO.getCreatedAt());
        orders.setUpdatedAt(ordersDTO.getUpdatedAt());
        orders.setStatus(ordersDTO.getStatus());

        return orders;
    }
}

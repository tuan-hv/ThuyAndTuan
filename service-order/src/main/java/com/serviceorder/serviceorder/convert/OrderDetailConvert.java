package com.serviceorder.serviceorder.convert;

import com.serviceorder.serviceorder.entity.OrderDetail;
import com.serviceorder.serviceorder.entity.Orders;
import dto.OrderdetailDTO;
import dto.OrdersDTO;

public class OrderDetailConvert {

    public static OrderdetailDTO convertOrderDetailToOrderDetailDTO(OrderDetail orderDetail){
        OrderdetailDTO orderdetailDTO = new OrderdetailDTO();

        orderdetailDTO.setDeltailId(orderDetail.getDeltailId());
        orderdetailDTO.setQuantity(orderDetail.getQuantity());
        orderdetailDTO.setCreatedAt(orderDetail.getCreatedAt());
        orderdetailDTO.setUpdatedAt(orderDetail.getUpdatedAt());
        orderdetailDTO.setStatus(orderDetail.getStatus());

        return orderdetailDTO;

    }

    public static OrderDetail convertOrderDetailDTOToOrderDetail(OrderdetailDTO orderdetailDTO){
        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setDeltailId(orderdetailDTO.getDeltailId());
        orderDetail.setQuantity(orderdetailDTO.getQuantity());
        orderDetail.setCreatedAt(orderdetailDTO.getCreatedAt());
        orderDetail.setUpdatedAt(orderdetailDTO.getUpdatedAt());
        orderDetail.setStatus(orderdetailDTO.getStatus());

        return orderDetail;
    }
}

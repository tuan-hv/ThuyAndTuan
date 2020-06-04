package com.serviceorder.services;

import com.serviceorder.converts.OrderConvert;
import com.serviceorder.converts.OrderDetailConvert;
import com.serviceorder.repositories.OrderDetailRepository;
import com.serviceorder.repositories.OrderRepository;
import com.serviceorder.repositories.ProductRepository;
import com.serviceorder.entities.OrderDetail;
import com.serviceorder.entities.Orders;
import dto.OrderdetailDTO;
import dto.OrdersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Created by: Thuy xinh
 * @Date: 03/06/2020
 */


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;


    public OrdersDTO createOrder(OrdersDTO ordersDTO){
        Orders orders =  OrderConvert.convertOrderDTOToOrder(ordersDTO);
        orderRepository.save(orders);

        List<OrderdetailDTO> orderDetailDTOList = ordersDTO.getOrderDetailEntities();
        orderDetailDTOList.forEach(o -> {
            OrderDetail orderDetail = OrderDetailConvert.convertOrderDetailDTOToOrderDetail(o);
            orderDetail.setOrders(orders);
            orderDetailRepository.save(orderDetail);
        });

        return ordersDTO;
    }


    public void changeOrderStatus(OrdersDTO ordersDTO){
        Optional<Orders> orders = orderRepository.findById(ordersDTO.getOrdersId());
        if(orders.isPresent()){
          switch (ordersDTO.getOrdersId()){
              case 0:
                  orders.get().setStatus(1);
                  break;
              case 1:
                  orders.get().setStatus(2);
                  break;
              case 2:
                  orders.get().setStatus(3);
                  break;
              default:
                  break;
          }
        }

    }








}

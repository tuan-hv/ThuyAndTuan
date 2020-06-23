package com.serviceorder.controllers;

import com.serviceorder.dto.OrdersDTO;
import com.serviceorder.exceptions.ResourceNotFoundException;
import com.serviceorder.servicesimp.OrderServiceImp;
import com.serviceorder.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 04/06/2020 - 11:01 AM
 * @created_by thuynt
 * @since 04/06/2020
 */
@RestController
@RequestMapping("/api")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderServiceImp orderServiceImp;

    @GetMapping("/orders")
    public ResponseEntity<List<OrdersDTO>> getAllOrders() {
        List<OrdersDTO> ordersDTOList = orderServiceImp.getAllOrders();
        if (ordersDTOList.isEmpty()) {
            LOGGER.info(Constant.NOT_FOUND);
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(ordersDTOList, HttpStatus.OK);
    }


    @GetMapping("/orders/{id}")
    public ResponseEntity<OrdersDTO> getOrderById(@PathVariable("id") int orderId) throws ResourceNotFoundException {
        OrdersDTO ordersDTO = orderServiceImp.getOrderByID(orderId);
        if (ordersDTO != null)
            return new ResponseEntity<>(ordersDTO, HttpStatus.OK);
        throw new ResourceNotFoundException(Constant.ORDER_NOT_FOUNT + orderId);
    }


    @PutMapping("/orders/{id}")
    public ResponseEntity<Object> changeOrderStatus(@PathVariable("id") int orderId) throws ResourceNotFoundException {
        OrdersDTO ordersDTO = orderServiceImp.changeOrderStatus(orderId);
        if (ordersDTO != null)
            return new ResponseEntity<>(ordersDTO, HttpStatus.OK);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/orders/getby/{username}")
    public ResponseEntity<List<OrdersDTO>> getOrderByUserName(@PathVariable("username") String username) throws ResourceNotFoundException {
        List<OrdersDTO> ordersDTOList = orderServiceImp.getOrderByUserName(username);
        if (ordersDTOList != null)
            return new ResponseEntity<>(ordersDTOList, HttpStatus.OK);
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/orders")
    public ResponseEntity<OrdersDTO> createOrder(@RequestBody OrdersDTO ordersDTO) {
        OrdersDTO orderCreated = orderServiceImp.createOrder(ordersDTO);
        if (orderCreated != null)
            return new ResponseEntity<>(ordersDTO, HttpStatus.CREATED);
        return ResponseEntity.badRequest().build();
    }

}

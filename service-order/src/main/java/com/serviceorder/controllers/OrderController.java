package com.serviceorder.controllers;

import com.serviceorder.services.OrderService;
import com.serviceorder.dto.OrdersDTO;
import com.serviceorder.exception.ResourceNotFoundException;
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
    OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrdersDTO>> getAllOrders() {
        List<OrdersDTO> ordersDTOList = orderService.getAllOrders();
        if (ordersDTOList.isEmpty()) {
            LOGGER.info("No orders found!");
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(ordersDTOList, HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrdersDTO> createOrder(@RequestBody OrdersDTO ordersDTO) {
        orderService.createOrder(ordersDTO);
        return new ResponseEntity<>(ordersDTO, HttpStatus.CREATED);

    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Object> changeOrderStatus(@PathVariable("orderId") int orderId) {
        orderService.changeOrderStatus(orderId);
        return new ResponseEntity<>(orderService.changeOrderStatus(orderId), HttpStatus.OK);

    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrdersDTO> getOrderById(@PathVariable("orderId") int orderId) throws ResourceNotFoundException {
        OrdersDTO ordersDTO = orderService.getOrderByID(orderId);
        if (ordersDTO != null) {
            return new ResponseEntity<>(ordersDTO, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }


}

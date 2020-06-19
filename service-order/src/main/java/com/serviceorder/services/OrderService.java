package com.serviceorder.services;

import com.serviceorder.converts.OrderConvert;
import com.serviceorder.converts.OrderDetailConvert;
import com.serviceorder.converts.ProductConvert;
import com.serviceorder.converts.UsersConvert;
import com.serviceorder.dto.OrderdetailDTO;
import com.serviceorder.dto.OrdersDTO;
import com.serviceorder.dto.ProductDTO;
import com.serviceorder.dto.UserDTO;
import com.serviceorder.entities.OrderDetail;
import com.serviceorder.entities.Orders;
import com.serviceorder.entities.Product;
import com.serviceorder.entities.Users;
import com.serviceorder.exceptions.ResourceNotFoundException;
import com.serviceorder.repositories.OrderDetailRepository;
import com.serviceorder.repositories.OrderRepository;
import com.serviceorder.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.serviceorder.utils.Constant.*;

/**
 * @Created by: Thuy xinh
 * @Date: 03/06/2020
 */


@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;


    public List<OrdersDTO> getAllOrders() {
        List<Orders> ordersList = orderRepository.findAll();
        List<OrdersDTO> ordersDTOList = new ArrayList<>();
        if (!ordersList.isEmpty()) {
            LOGGER.info(GET_SUCCESS, ORDER);
            ordersList.forEach(order -> {
                List<OrderdetailDTO> orderdetailDTOList = new ArrayList<>();
                List<OrderDetail> orderdetailList = order.getOrderDetails();
                Users users = order.getUsers();
                OrdersDTO ordersDTO = OrderConvert.convertOrdertoToOrderDTO(order);
                UserDTO userDTO = UsersConvert.convertUserToUserDTO(users);
                orderdetailList.forEach(orderDetail -> {
                    OrderdetailDTO orderdetailDTO = OrderDetailConvert.convertOrderDetailToOrderDetailDTO(orderDetail);
                    Product product = orderDetail.getProduct();
                    ProductDTO productDTO = ProductConvert.convertProductToProductDto(product);
                    orderdetailDTO.setProductDTO(productDTO);
                    orderdetailDTOList.add(orderdetailDTO);
                });
                ordersDTO.setOrderdetailDTOS(orderdetailDTOList);
                ordersDTO.setUserDTO(userDTO);
                ordersDTOList.add(ordersDTO);
            });
            return ordersDTOList;
        }
        LOGGER.warn(GET_FAIL, ORDER);
        return new ArrayList<>();
    }

    public OrdersDTO getOrderByID(int orderID) {
        Optional<Orders> orders = orderRepository.findById(orderID);
        OrdersDTO ordersDTO;
        if (orders.isPresent()) {
            List<OrderdetailDTO> orderdetailDTOList = new ArrayList<>();
            List<OrderDetail> orderdetailList = orders.get().getOrderDetails();
            Users users = orders.get().getUsers();
            UserDTO userDTO = UsersConvert.convertUserToUserDTO(users);
            ordersDTO = OrderConvert.convertOrdertoToOrderDTO(orders.get());
            orderdetailList.forEach(orderDetail -> {
                OrderdetailDTO orderdetailDTO = OrderDetailConvert.convertOrderDetailToOrderDetailDTO(orderDetail);
                Product product = orderDetail.getProduct();
                ProductDTO productDTO = ProductConvert.convertProductToProductDto(product);
                orderdetailDTO.setProductDTO(productDTO);
                orderdetailDTOList.add(orderdetailDTO);
            });
            ordersDTO.setUserDTO(userDTO);
            ordersDTO.setOrderdetailDTOS(orderdetailDTOList);
            LOGGER.info(FIND_BY_SUCCESS, ORDER);
            return ordersDTO;
        } else {
            LOGGER.warn(ORDER_NOT_FOUNT);
            return null;
        }
    }

    public List<OrdersDTO> getOrderByUserName(String username) throws ResourceNotFoundException {
        List<Orders> ordersList = orderRepository.findOrdersByUserName(username);
        List<OrdersDTO> ordersDTOList = new ArrayList<>();
        if (!ordersList.isEmpty()) {
            ordersList.forEach(order -> {
                List<OrderdetailDTO> orderdetailDTOList = new ArrayList<>();
                List<OrderDetail> orderdetailList = order.getOrderDetails();
                Users users = order.getUsers();
                OrdersDTO ordersDTO = OrderConvert.convertOrdertoToOrderDTO(order);
                UserDTO userDTO = UsersConvert.convertUserToUserDTO(users);
                orderdetailList.forEach(orderDetail -> {
                    OrderdetailDTO orderdetailDTO = OrderDetailConvert.convertOrderDetailToOrderDetailDTO(orderDetail);
                    Product product = orderDetail.getProduct();
                    ProductDTO productDTO = ProductConvert.convertProductToProductDto(product);
                    orderdetailDTO.setProductDTO(productDTO);
                    orderdetailDTOList.add(orderdetailDTO);
                });
                ordersDTO.setOrderdetailDTOS(orderdetailDTOList);
                ordersDTO.setUserDTO(userDTO);
                ordersDTOList.add(ordersDTO);
            });
            LOGGER.info(GET_ORDER_NAME_SUCCESS);
            return ordersDTOList;
        }
        LOGGER.error(GET_ORDER_NAME_FAIL);
        throw new ResourceNotFoundException(GET_ORDER_NAME_FAIL);
    }


    public OrdersDTO createOrder(OrdersDTO ordersDTO) {
        Orders orders = OrderConvert.convertOrderDTOToOrder(ordersDTO);
        Orders orderCreate = orderRepository.save(orders);
        UserDTO userDTO = ordersDTO.getUserDTO();
        Users users = UsersConvert.convertUserDTOToUser(userDTO);
        List<OrderDetail> orderDetailsList = new ArrayList<>();
        List<OrderdetailDTO> orderDetailDTOList = ordersDTO.getOrderdetailDTOS();
        orderDetailDTOList.forEach(o -> {
            OrderDetail orderDetail = OrderDetailConvert.convertOrderDetailDTOToOrderDetail(o);
            Optional<Product> product = productRepository.findById(o.getProductDTO().getProductId());
            if (product.isPresent()) {
                orderDetail.setProduct(product.get());
                orderDetail.setPrice(product.get().getPrice() * orderDetail.getQuantity());
                orderDetailsList.add(orderDetail);
                orders.setOrderDetails(orderDetailsList);
                orders.setUsers(users);
                orderDetail.setOrders(orders);
                orderDetailRepository.save(orderDetail);
            }
        });
        ordersDTO.setOrdersId(orderCreate.getOrderId());
        LOGGER.info(SAVE_SUCCESS, ORDER);
        return ordersDTO;
    }


    public OrdersDTO changeOrderStatus(int orderID) throws ResourceNotFoundException {
        Optional<Orders> orders = orderRepository.findById(orderID);
        OrdersDTO ordersDTO;
        if (orders.isPresent()) {
            ordersDTO = OrderConvert.convertOrdertoToOrderDTO(orders.get());
            switch (orders.get().getStatus()) {
                case 0:
                    orders.get().setStatus(ORDER_PROCESSING);
                    break;
                case 1:
                    orders.get().setStatus(ORDER_SUCCESS);
                    break;
                case 2:
                    orders.get().setStatus(ORDER_CANCLED);
                    break;
                default:
                    orders.get().setStatus(ORDER_CONFIRM);
                    break;
            }
            orderRepository.save(orders.get());
            ordersDTO.setOrdersId(orders.get().getOrderId());
            ordersDTO.setStatus(orders.get().getStatus());
            LOGGER.info(CHANGE_ORDER_SUCCESS);
            return ordersDTO;
        } else {
            LOGGER.info(CHANGE_ORDER_FAIL);
            throw new ResourceNotFoundException(ORDER_NOT_FOUNT + orderID);
        }
    }


}

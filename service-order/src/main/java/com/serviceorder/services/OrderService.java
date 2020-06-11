package com.serviceorder.services;

import com.serviceorder.converts.OrderConvert;
import com.serviceorder.converts.OrderDetailConvert;
import com.serviceorder.converts.ProductConvert;
import com.serviceorder.converts.UsersConvert;
import com.serviceorder.dto.UserDTO;
import com.serviceorder.entities.Product;
import com.serviceorder.entities.Users;
import com.serviceorder.exceptions.ResourceNotFoundException;
import com.serviceorder.repositories.OrderDetailRepository;
import com.serviceorder.repositories.OrderRepository;
import com.serviceorder.repositories.ProductRepository;
import com.serviceorder.entities.OrderDetail;
import com.serviceorder.entities.Orders;
import com.serviceorder.dto.OrderdetailDTO;
import com.serviceorder.dto.OrdersDTO;
import com.serviceorder.dto.ProductDTO;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public List<OrdersDTO> getAllOrders(){
        List<Orders> ordersList = orderRepository.findAll();
        List<OrdersDTO> ordersDTOList = new ArrayList<>();
        if(!ordersList.isEmpty()){
            LOGGER.info("get all Orders success!");
            ordersList.forEach(order -> {
                List<OrderdetailDTO> orderdetailDTOList = new ArrayList<>();
                List<OrderDetail> orderdetailList = order.getOrderDetailEntities();
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
                ordersDTO.setOrderDetailEntities(orderdetailDTOList);
                ordersDTO.setUserDTO(userDTO);
                ordersDTOList.add(ordersDTO);
            });
            return ordersDTOList;
        }
        LOGGER.info("get all Orders fail!");
        return new ArrayList<>();
    }

    public OrdersDTO getOrderByID(int orderID){
        Optional<Orders> orders = orderRepository.findById(orderID);
        OrdersDTO ordersDTO;
        if(orders.isPresent()){
            List<OrderdetailDTO> orderdetailDTOList = new ArrayList<>();
            List<OrderDetail> orderdetailList = orders.get().getOrderDetailEntities();
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
            ordersDTO.setOrderDetailEntities(orderdetailDTOList);
            LOGGER.info("get Orders by id successfully");
            return ordersDTO;
        }else {
            LOGGER.info("get Orders by id fail");
            return null;
        }
    }

    public List<OrdersDTO> getOrderByUserName(String username) throws ResourceNotFoundException{
        List<Orders> ordersList = orderRepository.findOrdersByUserName(username);
        List<OrdersDTO> ordersDTOList = new ArrayList<>();
        if(!ordersList.isEmpty()){
            LOGGER.info("get Orders by name success!");
            ordersList.forEach(order -> {
                List<OrderdetailDTO> orderdetailDTOList = new ArrayList<>();
                List<OrderDetail> orderdetailList = order.getOrderDetailEntities();
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
                ordersDTO.setOrderDetailEntities(orderdetailDTOList);
                ordersDTO.setUserDTO(userDTO);
                ordersDTOList.add(ordersDTO);
            });
            return ordersDTOList;
        }
        LOGGER.info("get Orders by name fail!");
        throw new ResourceNotFoundException("get order by name fail");
    }



    public OrdersDTO createOrder(OrdersDTO ordersDTO) {
        try {
            Orders orders = OrderConvert.convertOrderDTOToOrder(ordersDTO);
            Orders orderCreate = orderRepository.save(orders);
            UserDTO userDTO = ordersDTO.getUserDTO();
            Users users = UsersConvert.convertUserDTOToUser(userDTO);
            List<OrderDetail> orderDetailsList = new ArrayList<>();
            List<OrderdetailDTO> orderDetailDTOList = ordersDTO.getOrderDetailEntities();
            orderDetailDTOList.forEach(o -> {
                OrderDetail orderDetail = OrderDetailConvert.convertOrderDetailDTOToOrderDetail(o);
                Product product = null;
                try {
                    product = productRepository.findById(o.getProductDTO().getProductId()).orElseThrow(() -> new ResourceNotFoundException(" product not found"));
                } catch (ResourceNotFoundException e) {
                    LOGGER.info("product not found");
                    e.printStackTrace();
                }
                orderDetail.setProduct(product);
                orderDetail.setPrice(product.getPrice() * orderDetail.getQuantity());
                orderDetailsList.add(orderDetail);
                orders.setOrderDetailEntities(orderDetailsList);
                orders.setUsers(users);
                orderDetail.setOrders(orders);
                orderDetailRepository.save(orderDetail);

            });
            ordersDTO.setOrdersId(orderCreate.getOrderId());
            LOGGER.info("create order successfully!");
            return ordersDTO;
        } catch (Exception e) {
            LOGGER.error("Error when creating order!");
            return null;
        }

    }


    public OrdersDTO changeOrderStatus(int orderID) throws ResourceNotFoundException {
        Optional<Orders> orders = orderRepository.findById(orderID);
        OrdersDTO ordersDTO;
        if(orders.isPresent()){
            ordersDTO = OrderConvert.convertOrdertoToOrderDTO(orders.get());
            switch (orders.get().getStatus()){
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
                  orders.get().setStatus(0);
                  break;
          }
            orderRepository.save(orders.get());
            ordersDTO.setOrdersId(orders.get().getOrderId());
            ordersDTO.setStatus(orders.get().getStatus());
            LOGGER.info("change order status successfully!");
            return ordersDTO;
        }else {
            LOGGER.info("change order status fail!");
            throw new ResourceNotFoundException("Can not find order id "+ orderID);
        }
    }






}

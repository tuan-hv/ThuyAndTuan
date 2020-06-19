package com.serviceorder.service;

import com.serviceorder.converts.ProductConvert;
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
import com.serviceorder.services.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * ThuyAndTuan
 * @author thuynt
 * @created_at 04/06/2020 - 4:44 PM
 * @created_by thuynt
 * @since 04/06/2020
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock ProductRepository productRepository;

    @Before
    public void init() {
    }


    @Test
    public void getAllOrder(){
        List<Orders> ordersList = new ArrayList<>();
        Users user1 = new Users();
        Users user2 = new Users();
        Users user3 = new Users();

        List<OrderDetail> orderDetailList = new ArrayList<>();
        Orders orders1 = new Orders();
        Orders orders2 = new Orders();
        Orders orders3 = new Orders();

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProduct(new Product());
        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProduct(new Product());

        orderDetailList.add(orderDetail1);
        orderDetailList.add(orderDetail2);

        orders1.setUsers(user1);
        orders1.setOrderDetails(orderDetailList);
        orders2.setUsers(user2);
        orders2.setOrderDetails(orderDetailList);
        orders3.setUsers(user3);
        orders3.setOrderDetails(orderDetailList);

        ordersList.add(orders1);
        ordersList.add(orders2);
        ordersList.add(orders3);

        when(orderRepository.findAll()).thenReturn(ordersList);
        List<OrdersDTO> ordersDTOS = orderService.getAllOrders();
        assertEquals(3, ordersDTOS.size());
    }

    @Test
    public void getAllOrderFail(){
        when(orderRepository.findAll()).thenReturn(new ArrayList<>());
        List<OrdersDTO> ordersDTOS = orderService.getAllOrders();
        assertEquals(0, ordersDTOS.size());
    }

    @Test
    public void getOrderById() throws ResourceNotFoundException {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProduct(new Product());
        orderDetailList.add(orderDetail1);

        Orders orders = new Orders();
        orders.setOrderId(1);
        orders.setTotalPrice(120.0);
        orders.setStatus(0);
        orders.setOrderDetails(orderDetailList);
        orders.setUsers(new Users());

        when(orderRepository.findById(1)).thenReturn(Optional.of(orders));

        OrdersDTO ordersDTO = orderService.getOrderByID(1);
        assertEquals(1, ordersDTO.getOrdersId() );
        assertEquals(120.0, ordersDTO.getTotalPrice(), 0.0 );

    }

    @Test
    public void getOrderByIDFail() throws ResourceNotFoundException {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        OrdersDTO ordersDTO = orderService.getOrderByID(1);
        assertEquals(null, ordersDTO);
    }

    @Test
    public void changeOrderStatus0() throws ResourceNotFoundException {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        Orders orders = new Orders();
        orders.setOrderId(1);
        orders.setTotalPrice(120.0);
        orders.setStatus(0);
        orders.setOrderDetails(orderDetailList);

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orders));

        OrdersDTO ordersDTO = orderService.changeOrderStatus(1);
        assertEquals(1, ordersDTO.getStatus());
        assertEquals(1, ordersDTO.getOrdersId());

    }

    @Test
    public void changeOrderStatus1() throws ResourceNotFoundException {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        Orders orders = new Orders();
        orders.setOrderId(1);
        orders.setTotalPrice(120.0);
        orders.setStatus(1);
        orders.setOrderDetails(orderDetailList);

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orders));

        OrdersDTO ordersDTO = orderService.changeOrderStatus(1);
        assertEquals(2, ordersDTO.getStatus());
        assertEquals(1, ordersDTO.getOrdersId());

    }

    @Test
    public void changeOrderStatus2() throws ResourceNotFoundException {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        Orders orders = new Orders();
        orders.setOrderId(1);
        orders.setTotalPrice(120.0);
        orders.setStatus(2);
        orders.setOrderDetails(orderDetailList);

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orders));

        OrdersDTO ordersDTO = orderService.changeOrderStatus(1);
        assertEquals(3, ordersDTO.getStatus());
        assertEquals(1, ordersDTO.getOrdersId());

    }

    @Test
    public void changeOrderStatusDefault() throws ResourceNotFoundException {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        Orders orders = new Orders();
        orders.setOrderId(1);
        orders.setTotalPrice(120.0);
        orders.setStatus(5);
        orders.setOrderDetails(orderDetailList);

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orders));

        OrdersDTO ordersDTO = orderService.changeOrderStatus(1);
        assertEquals(0, ordersDTO.getStatus());
        assertEquals(1, ordersDTO.getOrdersId());

    }
    @Test
    public void changeOrderStatusThrowException(){
        when(orderRepository.findById(anyInt())).thenReturn(Optional.empty()).thenThrow(ResourceNotFoundException.class);
        assertThatThrownBy(() -> orderService.changeOrderStatus(anyInt())).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void addOrder(){
        List<OrderdetailDTO> orderDetailDTOList = new ArrayList<>();
        OrderdetailDTO orderDetailDTO = new OrderdetailDTO();
        orderDetailDTO.setAmount(1);
        orderDetailDTO.setStatus(1);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(1);
        productDTO.setDescription("abc");
        productDTO.setImage("image");
        productDTO.setProductName("name");
        orderDetailDTO.setProductDTO(productDTO);
        orderDetailDTO.setPrice(productDTO.getPrice() * orderDetailDTO.getAmount());
        orderDetailDTOList.add(orderDetailDTO);

        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setTotalPrice(123.0);
        ordersDTO.setStatus(0);
        ordersDTO.setOrderdetailDTOS(orderDetailDTOList);
        ordersDTO.setUserDTO(new UserDTO());

        when(productRepository.findById(1)).thenReturn(Optional.of(ProductConvert.convertProductDtoToProduct(productDTO)));

        when(orderRepository.save(any(Orders.class))).thenAnswer((Answer<Orders>) invocation ->  {
            Orders orders1 = (Orders) invocation.getArguments()[0];
            orders1.setOrderId(1);
            return orders1;
        });
        when(orderDetailRepository.save(any(OrderDetail.class))).thenAnswer((Answer<OrderDetail>) invocation ->  {
            OrderDetail orderdetail1 = (OrderDetail) invocation.getArguments()[0];
            orderdetail1.setDeltailId(1);
            return orderdetail1;
        });
        assertEquals(0, ordersDTO.getOrdersId());

        OrdersDTO createOrder = orderService.createOrder(ordersDTO);

        assertNotNull(createOrder.getOrdersId());

        assertEquals(1, createOrder.getOrdersId());
    }

//    @Test
//    public void addOrderFail(){
//        List<OrderdetailDTO> orderDetailDTOList = new ArrayList<>();
//        OrderdetailDTO orderDetailDTO = new OrderdetailDTO();
//        orderDetailDTO.setAmount(1);
//        orderDetailDTO.setStatus(1);
//
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setProductId(1);
//        productDTO.setDescription("abc");
//        productDTO.setImage("image");
//        productDTO.setProductName("name");
//        orderDetailDTO.setProductDTO(productDTO);
//        orderDetailDTO.setPrice(productDTO.getPrice() * orderDetailDTO.getAmount());
//        orderDetailDTOList.add(orderDetailDTO);
//
//        OrdersDTO ordersDTO = new OrdersDTO();
//        ordersDTO.setTotalPrice(123.0);
//        ordersDTO.setStatus(0);
//        ordersDTO.setOrderdetailDTOS(orderDetailDTOList);
//        ordersDTO.setUserDTO(new UserDTO());
//
//        when(orderRepository.save(any(Orders.class))).thenReturn(OrderConvert.convertOrderDTOToOrder(ordersDTO));
//        when(productRepository.findById(anyInt())).thenReturn(Optional.of(new Product()));
//        when(orderDetailRepository.save(any())).thenReturn(orderDetailDTO);
//        //OrdersDTO createOrderDTO = orderService.createOrder(ordersDTO);
//        assertThatThrownBy(() -> orderService.createOrder(any(OrdersDTO.class))).isInstanceOf(RuntimeException.class);
//
//        //  assertEquals(RuntimeException.class, createOrderDTO);
//    }

    @Test
    public void getOrderByUsername() throws ResourceNotFoundException {
        List<Orders> ordersList = new ArrayList<>();
        Users user1 = new Users();
        user1.setUserName("name1");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        Orders orders1 = new Orders();
        Orders orders2 = new Orders();
        Orders orders3 = new Orders();

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProduct(new Product());
        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProduct(new Product());

        orderDetailList.add(orderDetail1);
        orderDetailList.add(orderDetail2);

        orders1.setUsers(user1);
        orders1.setOrderDetails(orderDetailList);
        orders2.setUsers(user1);
        orders2.setOrderDetails(orderDetailList);
        orders3.setUsers(user1);
        orders3.setOrderDetails(orderDetailList);

        ordersList.add(orders1);
        ordersList.add(orders2);
        ordersList.add(orders3);

        when(orderRepository.findOrdersByUserName("name1")).thenReturn(ordersList);
        List<OrdersDTO> ordersDTOS = orderService.getOrderByUserName("name1");
        assertEquals(3, ordersDTOS.size());
    }


    @Test
    public void getOrderByUsernameThrowException() {
        when(orderRepository.findOrdersByUserName(anyString())).thenReturn(new ArrayList<>()).thenThrow(ResourceNotFoundException.class);
        assertThatThrownBy(() -> orderService.getOrderByUserName(anyString())).isInstanceOf(ResourceNotFoundException.class);
    }


}

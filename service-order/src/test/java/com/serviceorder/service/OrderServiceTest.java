package com.serviceorder.service;

import com.serviceorder.converts.ProductConvert;
import com.serviceorder.entities.OrderDetail;
import com.serviceorder.entities.Orders;
import com.serviceorder.repositories.OrderDetailRepository;
import com.serviceorder.repositories.OrderRepository;
import com.serviceorder.repositories.ProductRepository;
import com.serviceorder.services.OrderService;
import dto.OrderdetailDTO;
import dto.OrdersDTO;
import dto.ProductDTO;
import exception.ResourceNotFoundException;
import jdk.nashorn.internal.objects.NativeUint8Array;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * ThuyAndTuan
 *
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
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetAllOrder(){
        List<Orders> ordersList = new ArrayList<>();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        Orders orders1 = new Orders();
        Orders orders2 = new Orders();
        Orders orders3 = new Orders();

        orders1.setOrderDetailEntities(orderDetailList);
        orders2.setOrderDetailEntities(orderDetailList);
        orders3.setOrderDetailEntities(orderDetailList);

        ordersList.add(orders1);
        ordersList.add(orders2);
        ordersList.add(orders3);

        when(orderRepository.findAll()).thenReturn(ordersList);
        List<OrdersDTO> ordersDTOS = orderService.getAllOrders();
        assertEquals(3, ordersDTOS.size());
    }

    @Test
    public void testGetAllOrderFail(){
        when(orderRepository.findAll()).thenReturn(new ArrayList<>());
        List<OrdersDTO> ordersDTOS = orderService.getAllOrders();
        assertEquals(0, ordersDTOS.size());
    }

    @Test
    public void testGetOrderById() throws ResourceNotFoundException {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        Orders orders = new Orders();
        orders.setOrderId(1);
        orders.setTotalPrice(120.0);
        orders.setStatus(0);
        orders.setOrderDetailEntities(orderDetailList);

        when(orderRepository.findById(1)).thenReturn(Optional.of(orders));

        OrdersDTO ordersDTO = orderService.getOrderByID(1);
        assertEquals(1, ordersDTO.getOrdersId() );
        assertEquals(120.0, ordersDTO.getTotalPrice(), 0.0 );

    }

    @Test
    public void testGetOrderByIDFail() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        OrdersDTO ordersDTO = orderService.getOrderByID(1);
        assertEquals(null, ordersDTO);
    }

    @Test
    public void changeOrderStatus(){
        List<OrderDetail> orderDetailList = new ArrayList<>();
        Orders orders = new Orders();
        orders.setOrderId(1);
        orders.setTotalPrice(120.0);
        orders.setStatus(0);
        orders.setOrderDetailEntities(orderDetailList);

        when(orderRepository.findById(1)).thenReturn(Optional.of(orders));

        OrdersDTO ordersDTO = orderService.changeOrderStatus(1);
        assertEquals(1, ordersDTO.getStatus());
        assertEquals(1, ordersDTO.getOrdersId());

    }

    @Test
    public void changeOrderStatusfail(){
        when(orderRepository.findById(1)).thenReturn(Optional.empty());
        OrdersDTO ordersDTO = orderService.changeOrderStatus(1);
        assertEquals(null, ordersDTO);

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
        ordersDTO.setOrderDetailEntities(orderDetailDTOList);

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

    @Test
    public void addOrderFail(){
        OrdersDTO ordersDTO = new OrdersDTO();
        when(orderRepository.save(any(Orders.class))).thenThrow(Exception.class);
        OrdersDTO createOrderDTO = orderService.createOrder(ordersDTO);
        assertEquals(null, createOrderDTO);
    }

}

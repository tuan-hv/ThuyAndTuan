package com.serviceorder.controller;

import com.serviceorder.controllers.OrderController;
import com.serviceorder.dto.OrderdetailDTO;
import com.serviceorder.dto.OrdersDTO;
import com.serviceorder.dto.ProductDTO;
import com.serviceorder.dto.UserDTO;
import com.serviceorder.exceptions.GlobalExceptionHandler;
import com.serviceorder.servicesimp.OrderServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.serviceorder.controller.ProductControllerTest.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 05/06/2020 - 3:35 PM
 * @created_by thuynt
 * @since 05/06/2020
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = OrderController.class)
public class OrderControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderServiceImp orderServiceImp;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(orderController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilters(new CORSFilter())
                .build();
    }

    @Test
    @DisplayName("Test GetAllOrders()")
    public void testGetAllOrders() throws Exception {
        List<OrderdetailDTO> orderdetailDTOList1 = new ArrayList<>();
        List<OrderdetailDTO> orderdetailDTOList2 = new ArrayList<>();
        List<OrderdetailDTO> orderdetailDTOList3 = new ArrayList<>();

        List<OrdersDTO> ordersDTOList = Arrays.asList(
                new OrdersDTO(1, 123, orderdetailDTOList1, null),
                new OrdersDTO(2, 123, orderdetailDTOList2, null),
                new OrdersDTO(3, 123, orderdetailDTOList3, null));

        when(orderServiceImp.getAllOrders()).thenReturn(ordersDTOList);
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].ordersId", is(1)))
                .andExpect(jsonPath("$[1].ordersId", is(2)))
                .andExpect(jsonPath("$[2].ordersId", is(3)));

    }


    @Test
    @DisplayName("Test getAllOrderFail ")
    public void testGetAllOrderFail() throws Exception {
        when(orderServiceImp.getAllOrders()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetOrderbyId() throws Exception {
        List<OrderdetailDTO> orderdetailDTOList1 = new ArrayList<>();
        OrdersDTO ordersDTO = new OrdersDTO(1, 123, orderdetailDTOList1, null);
        when(orderServiceImp.getOrderByID(anyInt())).thenReturn(ordersDTO);

        mockMvc.perform(get("/api/orders/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ordersId", is(1)))
                .andExpect(jsonPath("$.totalPrice", is(123.0)));
    }

    @Test
    public void testGetOrderbyIdFail() throws Exception {
        when(orderServiceImp.getOrderByID(anyInt())).thenReturn(null);
        mockMvc.perform(get("/api/orders/{id}", 111))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testChangeStatus() throws Exception {
        List<OrderdetailDTO> orderDetailList = new ArrayList<>();
        OrdersDTO orderDTO = new OrdersDTO();
        orderDTO.setOrdersId(1);
        orderDTO.setTotalPrice(120.0);
        orderDTO.setStatus(1);
        orderDTO.setOrderdetailDTOS(orderDetailList);
        when(orderServiceImp.changeOrderStatus(1)).thenReturn(orderDTO);

        mockMvc.perform(put("/api/orders/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ordersId", is(1)))
                .andExpect(jsonPath("$.status", is(1)));
    }

    @Test
    public void testChangeStatusfail() throws Exception {
        when(orderServiceImp.changeOrderStatus(1)).thenReturn(null);
        mockMvc.perform(put("/api/orders/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOrder() throws Exception {
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
        ordersDTO.setOrderdetailDTOS(null);

        when(orderServiceImp.createOrder(Matchers.any(OrdersDTO.class))).thenReturn(ordersDTO);
        mockMvc.perform(
                post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ordersDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalPrice", is(123.0)))
                .andExpect(jsonPath("$.status", is(0)));
    }

    @Test
    public void testCreateOrderFail() throws Exception {
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setTotalPrice(123.0);
        ordersDTO.setStatus(0);
        ordersDTO.setOrderdetailDTOS(null);
        when(orderServiceImp.createOrder(Matchers.any(OrdersDTO.class))).thenReturn(null);
        mockMvc.perform(
                post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ordersDTO)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testGetOrdersByName() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setUserName("name");
        List<OrdersDTO> ordersDTOList = Arrays.asList(
                new OrdersDTO(1, 123, new ArrayList<>(), userDTO),
                new OrdersDTO(2, 123, new ArrayList<>(), userDTO),
                new OrdersDTO(3, 123, new ArrayList<>(), userDTO));

        when(orderServiceImp.getOrderByUserName("name")).thenReturn(ordersDTOList);
        mockMvc.perform(get("/api/orders/getby/{username}", "name"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testGetOrdersByNameFail() throws Exception {
        when(orderServiceImp.getOrderByUserName(anyString())).thenReturn(null);
        mockMvc.perform(get("/api/orders/getby/{username}", "name"))
                .andExpect(status().isNotFound());
    }


}







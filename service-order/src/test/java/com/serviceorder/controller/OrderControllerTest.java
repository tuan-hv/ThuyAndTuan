package com.serviceorder.controller;

import com.serviceorder.controllers.OrderController;
import com.serviceorder.entities.OrderDetail;
import com.serviceorder.repositories.OrderRepository;
import com.serviceorder.services.OrderService;
import dto.OrderdetailDTO;
import dto.OrdersDTO;
import dto.ProductDTO;
import exception.GlobalExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;


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
    public void testGetAllOrders() {
        List<OrderdetailDTO> orderdetailDTOList1 = new ArrayList<>();
        List<OrderdetailDTO> orderdetailDTOList2 = new ArrayList<>();
        List<OrderdetailDTO> orderdetailDTOList3 = new ArrayList<>();

        List<OrdersDTO> ordersDTOList = Arrays.asList(
                new OrdersDTO(1, 123, orderdetailDTOList1),
                new OrdersDTO(2, 123, orderdetailDTOList2),
                new OrdersDTO(3, 123, orderdetailDTOList3));

        when(orderService.getAllOrders()).thenReturn(ordersDTOList);
        try {
            mockMvc.perform(get("/api/orders"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[0].ordersId", is(1)))
                    .andExpect(jsonPath("$[1].ordersId", is(2)))
                    .andExpect(jsonPath("$[2].ordersId", is(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("Test getAllOrderFail ")
    public void testGetAllOrderFail() throws Exception {
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetOrderbyId() throws Exception {
        List<OrderdetailDTO> orderdetailDTOList1 = new ArrayList<>();
        OrdersDTO ordersDTO = new OrdersDTO(1, 123, orderdetailDTOList1);
        when(orderService.getOrderByID(anyInt())).thenReturn(ordersDTO);

        mockMvc.perform(get("/api/orders/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ordersId", is(1)))
                .andExpect(jsonPath("$.totalPrice", is(123.0)));
    }

    @Test
    public void testGetOrderbyIdFail() throws Exception {
        when(orderService.getOrderByID(anyInt())).thenReturn(null);

        mockMvc.perform(get("/api/orders/{id}", 111))
                .andExpect(status().isNotFound());
    }



}
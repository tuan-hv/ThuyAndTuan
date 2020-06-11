package com.serviceorder.converts;

import com.serviceorder.dto.OrdersDTO;
import com.serviceorder.dto.UserDTO;
import com.serviceorder.entities.OrderDetail;
import com.serviceorder.entities.Orders;
import com.serviceorder.entities.Users;

import java.util.ArrayList;
import java.util.List;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 11/06/2020 - 11:08 AM
 * @created_by thuynt
 * @since 11/06/2020
 */
public class UsersConvert {
    public static UserDTO convertUserToUserDTO(Users users){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(users.getUserId());
        userDTO.setUserName(users.getUserName());

//        List<Orders> ordersList = users.getOrders();
//        List<OrdersDTO> ordersDTOList = new ArrayList<>();
//        ordersList.forEach(o -> {
//            OrdersDTO ordersDTO = OrderConvert.convertOrdertoToOrderDTO(o);
//            ordersDTOList.add(ordersDTO);
//        });

        return userDTO;
    }

    public static Users convertUserDTOToUser(UserDTO userDTO){
        Users users = new Users();
        users.setUserId(userDTO.getUserId());
        users.setUserName(userDTO.getUserName());
        return users;
    }
}

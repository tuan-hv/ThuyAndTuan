package com.serviceorder.converts;

import com.serviceorder.dto.UserDTO;
import com.serviceorder.entities.Users;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 11/06/2020 - 11:08 AM
 * @created_by thuynt
 * @since 11/06/2020
 */
public class UsersConvert {

    private UsersConvert() {
        throw new IllegalStateException("Utility class");
    }

    public static UserDTO convertUserToUserDTO(Users users){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(users.getUserId());
        userDTO.setUserName(users.getUserName());

        return userDTO;
    }

    public static Users convertUserDTOToUser(UserDTO userDTO){
        Users users = new Users();
        users.setUserId(userDTO.getUserId());
        users.setUserName(userDTO.getUserName());
        return users;
    }
}

package com.serviceorder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 11/06/2020 - 11:09 AM
 * @created_by thuynt
 * @since 11/06/2020
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO extends AbstractDTO {
    private int userId;
    private String userName;

    private List<OrdersDTO> ordersDTOList;

}

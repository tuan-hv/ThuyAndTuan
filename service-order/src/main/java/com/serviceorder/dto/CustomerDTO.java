package com.serviceorder.dto;

import com.serviceorder.entities.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 11:03 AM
 * @created_by thuynt
 * @since 08/07/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO extends AbstractDTO{

    private int customerId;
    private String customerName;
    private String customerPhone;
    private AccountDTO accountDTO;



}

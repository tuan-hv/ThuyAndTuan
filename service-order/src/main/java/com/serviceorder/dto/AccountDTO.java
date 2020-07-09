package com.serviceorder.dto;

import com.serviceorder.entities.AccountType;
import com.serviceorder.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class AccountDTO extends AbstractDTO {
    private int accountId;
    private String accountNumber;
    private Long accountBalance;
    private AccountTypeDTO accountTypeDTO;
    private CustomerDTO customerDTO;


}

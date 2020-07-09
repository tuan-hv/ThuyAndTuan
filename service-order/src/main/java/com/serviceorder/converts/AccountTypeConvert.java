package com.serviceorder.converts;

import com.serviceorder.dto.AccountDTO;
import com.serviceorder.dto.AccountTypeDTO;
import com.serviceorder.entities.AccountType;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 11:08 AM
 * @created_by thuynt
 * @since 08/07/2020
 */
public class AccountTypeConvert {

    public static AccountTypeDTO convertTypeToTypeDTO(AccountType accountType){
        AccountTypeDTO accountTypeDTO = new AccountTypeDTO();
        accountTypeDTO.setTypeId(accountType.getTypeId());
        accountTypeDTO.setTypeName(accountType.getTypeName());
        accountTypeDTO.setCreatedAt(accountType.getCreatedAt());
        accountTypeDTO.setUpdatedAt(accountType.getUpdatedAt());

        return accountTypeDTO;
    }

    public static AccountType convertTypeDTOToType(AccountTypeDTO accountTypeDTO){
        AccountType accountType = new AccountType();
        accountType.setTypeId(accountTypeDTO.getTypeId());
        accountType.setTypeName(accountTypeDTO.getTypeName());
        accountType.setCreatedAt(accountTypeDTO.getCreatedAt());
        accountType.setUpdatedAt(accountTypeDTO.getUpdatedAt());

        return accountType;
    }
}

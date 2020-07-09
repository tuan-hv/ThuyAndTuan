package com.serviceorder.services;

import com.serviceorder.dto.AccountTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 2:06 PM
 * @created_by thuynt
 * @since 08/07/2020
 */
public interface AccountTypeService {
    Optional<AccountTypeDTO> findAccountTypeById(int typeId);

    List<AccountTypeDTO> findAllAccountType();

    AccountTypeDTO addAccountType(AccountTypeDTO accountTypeDTO);
}

package com.serviceorder.services;

import com.serviceorder.dto.AccountDTO;
import com.serviceorder.dto.AccountTypeDTO;
import com.serviceorder.exceptions.FileDuplicateException;
import com.serviceorder.exceptions.ResourceNotFoundException;
import javassist.NotFoundException;

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
    Optional<AccountTypeDTO> findAccountTypeById(int typeId) throws ResourceNotFoundException;

    List<AccountTypeDTO> findAllAccountType() throws ResourceNotFoundException;

    AccountTypeDTO addAccountType(AccountTypeDTO accountTypeDTO) throws FileDuplicateException;

    Boolean isAccountTypeExist(String typeName) ;

    AccountTypeDTO updateAccountType(int typeId, AccountTypeDTO accountTypeDTO) throws ResourceNotFoundException;

}

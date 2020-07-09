package com.serviceorder.services;

import com.serviceorder.dto.AccountDTO;
import com.serviceorder.entities.Account;

import java.util.Optional;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 09/07/2020 - 11:30 AM
 * @created_by thuynt
 * @since 09/07/2020
 */
public interface AccountService {
    Optional<AccountDTO> findAccountById(int accountId);

    AccountDTO addAccount(AccountDTO accountDTO);


}

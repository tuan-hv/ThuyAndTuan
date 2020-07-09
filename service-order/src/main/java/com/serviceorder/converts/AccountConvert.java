package com.serviceorder.converts;

import com.serviceorder.dto.AccountDTO;
import com.serviceorder.entities.Account;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 11:08 AM
 * @created_by thuynt
 * @since 08/07/2020
 */
public class AccountConvert {

    public static Account convertAccountDTOToAccount(AccountDTO accountDTO){
        Account account = new Account();
        account.setAccountId(accountDTO.getAccountId());
        account.setAccountBalance(accountDTO.getAccountBalance());
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setStatus(accountDTO.getStatus());
        account.setCreatedAt(accountDTO.getCreatedAt());
        account.setUpdatedAt(accountDTO.getUpdatedAt());
        return account;
    }

    public static AccountDTO convertAccountToAccountDTO(Account account){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountId(account.getAccountId());
        accountDTO.setAccountBalance(account.getAccountBalance());
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setStatus(account.getStatus());
        accountDTO.setCreatedAt(account.getCreatedAt());
        accountDTO.setUpdatedAt(account.getUpdatedAt());
        return accountDTO;
    }
}

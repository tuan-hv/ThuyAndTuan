package com.serviceorder.servicesimp;

import com.serviceorder.converts.AccountConvert;
import com.serviceorder.dto.AccountDTO;
import com.serviceorder.entities.Account;
import com.serviceorder.repositories.AccountRepository;
import com.serviceorder.services.AccountService;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 09/07/2020 - 11:32 AM
 * @created_by thuynt
 * @since 09/07/2020
 */
@Service
public class AccountServiceImp implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Optional<AccountDTO> findAccountById(int accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isPresent()){
            AccountDTO accountDTO = AccountConvert.convertAccountToAccountDTO(account.get());
            return Optional.of(accountDTO);
        }
        return Optional.empty();
    }

    @Override
    public AccountDTO addAccount(AccountDTO accountDTO) {
        Account account = AccountConvert.convertAccountDTOToAccount(accountDTO);
        accountRepository.save(account);
        accountDTO.setAccountId(account.getAccountId());
        return accountDTO;
    }
}

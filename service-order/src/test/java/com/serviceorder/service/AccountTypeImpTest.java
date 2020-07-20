package com.serviceorder.service;

import com.serviceorder.dto.AccountTypeDTO;
import com.serviceorder.entities.AccountType;
import com.serviceorder.exceptions.ResourceNotFoundException;
import com.serviceorder.repositories.AccountTypeRepository;
import com.serviceorder.servicesimp.AccountTypeServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;


/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 14/07/2020 - 3:27 PM
 * @created_by thuynt
 * @since 14/07/2020
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountTypeImpTest {
    @InjectMocks
    private AccountTypeServiceImp accountTypeServiceImp;

    @Mock
    private AccountTypeRepository accountTypeRepository;
    AccountType accountType;
    AccountTypeDTO accountTypeDTO1;
    AccountTypeDTO accountTypeDTO2;
    List<AccountType> accountTypeList;




    @Before
    public void init() {
        accountType = new AccountType();
        accountType.setTypeId(1);
        accountType.setTypeName("abc");
        accountType.setStatus(1);

        accountTypeList = Arrays.asList(new AccountType(), new AccountType(), new AccountType());

        accountTypeDTO1 = new AccountTypeDTO(2, "type2", 1);
    }

    @Test
    public void testGetAllAccountType() throws ResourceNotFoundException {
        when(accountTypeRepository.findAll()).thenReturn(accountTypeList);
        List<AccountTypeDTO> accountTypeDTOList = accountTypeServiceImp.findAllAccountType();



    }

}

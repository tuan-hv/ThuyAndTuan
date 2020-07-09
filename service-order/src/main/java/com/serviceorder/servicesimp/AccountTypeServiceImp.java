package com.serviceorder.servicesimp;

import com.serviceorder.converts.AccountConvert;
import com.serviceorder.converts.AccountTypeConvert;
import com.serviceorder.dto.AccountTypeDTO;
import com.serviceorder.entities.AccountType;
import com.serviceorder.repositories.AccountTypeRepository;
import com.serviceorder.repositories.specifications.AccountTypeSpecification;
import com.serviceorder.services.AccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.serviceorder.repositories.specifications.AccountTypeSpecification.hasId;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 2:11 PM
 * @created_by thuynt
 * @since 08/07/2020
 */
@Service
public class AccountTypeServiceImp implements AccountTypeService {
    @Autowired
    AccountTypeRepository accountTypeRepository;



    @Override
    public Optional<AccountTypeDTO> findAccountTypeById(int typeId) {
        Optional<AccountType> accountType = accountTypeRepository.findOne(Specification.where(hasId(typeId)));
        if(accountType.isPresent()){
            AccountTypeDTO accountTypeDTO = AccountTypeConvert.convertTypeToTypeDTO(accountType.get());
            return Optional.of(accountTypeDTO);
        }
        return Optional.empty();
    }



    @Override
    public List<AccountTypeDTO> findAllAccountType() {
        List<AccountType> accountTypeList = accountTypeRepository.findAll();
        List<AccountTypeDTO> accountTypeDTOList = new ArrayList<>();
        accountTypeList.forEach(accountType -> {
            AccountTypeDTO accountTypeDTO = AccountTypeConvert.convertTypeToTypeDTO(accountType);
            accountTypeDTOList.add(accountTypeDTO);
        });
        return accountTypeDTOList;
    }

    @Override
    public AccountTypeDTO addAccountType(AccountTypeDTO accountTypeDTO) {
        AccountType accountType = AccountTypeConvert.convertTypeDTOToType(accountTypeDTO);
        accountTypeDTO.setTypeId(accountType.getTypeId());
        accountTypeRepository.save(accountType);
        return accountTypeDTO;
    }

}

package com.serviceorder.servicesimp;

import com.serviceorder.converts.AccountTypeConvert;
import com.serviceorder.dto.AccountDTO;
import com.serviceorder.dto.AccountTypeDTO;
import com.serviceorder.entities.Account;
import com.serviceorder.entities.AccountType;
import com.serviceorder.exceptions.FileDuplicateException;
import com.serviceorder.exceptions.ResourceNotFoundException;
import com.serviceorder.repositories.AccountTypeRepository;
import com.serviceorder.services.AccountTypeService;
import com.serviceorder.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountTypeServiceImp.class);

    @Autowired
    AccountTypeRepository accountTypeRepository;



    @Override
    public Optional<AccountTypeDTO> findAccountTypeById(int typeId) throws ResourceNotFoundException {
        try {
            Optional<AccountType> accountType = accountTypeRepository.findOne(Specification.where(hasId(typeId)));
            AccountTypeDTO accountTypeDTO = AccountTypeConvert.convertTypeToTypeDTO(accountType.get());
            LOGGER.info(Constant.FIND_BY_ID);
            return Optional.of(accountTypeDTO);
        }catch (Exception e){
            LOGGER.warn(Constant.NOT_FOUND);
            throw new ResourceNotFoundException("not found 2");
        }
    }



    @Override
    public List<AccountTypeDTO> findAllAccountType() throws ResourceNotFoundException{
        List<AccountType> accountTypeList = accountTypeRepository.findAll();
        List<AccountTypeDTO> accountTypeDTOList = new ArrayList<>();
        if(!accountTypeList.isEmpty()){
            accountTypeList.forEach(accountType -> {
                AccountTypeDTO accountTypeDTO = AccountTypeConvert.convertTypeToTypeDTO(accountType);
                accountTypeDTOList.add(accountTypeDTO);
            });
            LOGGER.info(Constant.GET_SUCCESS);
            return accountTypeDTOList;
        }
        throw new ResourceNotFoundException("ss");
    }

    @Override
    public AccountTypeDTO addAccountType(AccountTypeDTO accountTypeDTO) throws FileDuplicateException{
        if(!isAccountTypeExist(accountTypeDTO.getTypeName())){
            AccountType accountType = AccountTypeConvert.convertTypeDTOToType(accountTypeDTO);
            accountTypeDTO.setTypeId(accountType.getTypeId());
            accountTypeRepository.save(accountType);
            return accountTypeDTO;

       }throw new FileDuplicateException("account type da ton tai roi!");

    }

    @Override
    public Boolean isAccountTypeExist(String typeName)  {
        return accountTypeRepository.findAccountTypeByTypeName(typeName).isPresent();
    }

    @Override
    public AccountTypeDTO updateAccountType(int typeId, AccountTypeDTO accountTypeDTO) throws ResourceNotFoundException {
        Optional<AccountType> accountType = accountTypeRepository.findById(typeId);
        if(accountType.isPresent()){
            accountTypeDTO.setTypeId(typeId);
            accountTypeRepository.updateAccountType(typeId, accountTypeDTO.getTypeName(), accountTypeDTO.getStatus());
            //accountTypeRepository.save(AccountTypeConvert.convertTypeDTOToType(accountTypeDTO));
           return accountTypeDTO;
        }
        throw  new ResourceNotFoundException("Khong tim thay object de cap nhat");
    }

}

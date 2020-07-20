package com.serviceorder.controllers;

import com.serviceorder.apiresponse.APIResponse;
import com.serviceorder.dto.AccountTypeDTO;
import com.serviceorder.exceptions.ResourceNotFoundException;
import com.serviceorder.servicesimp.AccountTypeServiceImp;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.serviceorder.apiresponse.APIResponse.setDataForAPIResponse;
import static com.serviceorder.utils.Constant.*;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 2:34 PM
 * @created_by thuynt
 * @since 08/07/2020
 */
@RestController
@RequestMapping("/api")
public class AccountTypeController {
    @Autowired
    AccountTypeServiceImp accountTypeServiceImp;

    @PostMapping("/type")
    public ResponseEntity<APIResponse<AccountTypeDTO>> addAccountType(@Valid @RequestBody AccountTypeDTO accountTypeDTO){
        APIResponse apiResponse = setDataForAPIResponse(accountTypeServiceImp.addAccountType(accountTypeDTO), SAVE_SUCCESS, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/type/{id}")
    public ResponseEntity<APIResponse<AccountTypeDTO>> updateAccountType(@PathVariable("id") int accountId, @Valid @RequestBody AccountTypeDTO accountTypeDTO) throws ResourceNotFoundException {
        APIResponse apiResponse = setDataForAPIResponse(accountTypeServiceImp.updateAccountType(accountId, accountTypeDTO), SAVE_SUCCESS, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/type/{id}")
    public ResponseEntity<APIResponse<Optional<AccountTypeDTO>>> findAccountTypeById(@PathVariable("id") int accountId) throws ResourceNotFoundException {
        APIResponse apiResponse = setDataForAPIResponse(accountTypeServiceImp.findAccountTypeById(accountId), FIND_BY_ID, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @GetMapping("/type")
    public ResponseEntity<APIResponse<List<AccountTypeDTO>>> findAllAccountType() throws ResourceNotFoundException {
        APIResponse apiResponse = setDataForAPIResponse(accountTypeServiceImp.findAllAccountType(), GET_SUCCESS, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }





}

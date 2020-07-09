package com.serviceorder.controllers;

import com.serviceorder.apiresponse.APIResponse;
import com.serviceorder.dto.AccountTypeDTO;
import com.serviceorder.servicesimp.AccountTypeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.serviceorder.apiresponse.APIResponse.setDataForAPIResponse;

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
    public ResponseEntity<APIResponse<AccountTypeDTO>> addAccountType(@RequestBody AccountTypeDTO accountTypeDTO){
        APIResponse apiResponse = setDataForAPIResponse(accountTypeServiceImp.addAccountType(accountTypeDTO), "Create account type successful", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/type/id{id}")
    public ResponseEntity<APIResponse<Optional<AccountTypeDTO>>> findAccountTypeById(@PathVariable("id") int accountId){
        APIResponse apiResponse = setDataForAPIResponse(accountTypeServiceImp.findAccountTypeById(accountId), "Find by id successful", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @GetMapping("/type")
    public ResponseEntity<APIResponse<List<AccountTypeDTO>>> findAllAccountType(){
        APIResponse apiResponse = setDataForAPIResponse(accountTypeServiceImp.findAllAccountType(), "Find all account type successful", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }





}

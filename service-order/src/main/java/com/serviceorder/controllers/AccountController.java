package com.serviceorder.controllers;

import com.serviceorder.apiresponse.APIResponse;
import com.serviceorder.dto.AccountDTO;
import com.serviceorder.entities.Account;
import com.serviceorder.servicesimp.AccountServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.serviceorder.apiresponse.APIResponse.setDataForAPIResponse;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 3:45 PM
 * @created_by thuynt
 * @since 08/07/2020
 */
@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountServiceImp accountServiceImp;

    @GetMapping("/account/id/{id}")
    ResponseEntity<APIResponse<Optional<AccountDTO>>> findAccountById(@PathVariable("id") int accountId){
        Optional<AccountDTO> account = accountServiceImp.findAccountById(accountId);
        APIResponse apiResponse = setDataForAPIResponse(account, "find account by id successful", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }




}

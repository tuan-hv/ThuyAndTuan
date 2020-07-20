package com.serviceorder.controllers;

import com.serviceorder.apiresponse.APIResponse;
import com.serviceorder.converts.CustomerConvert;
import com.serviceorder.dto.CustomerDTO;
import com.serviceorder.entities.Customer;
import com.serviceorder.servicesimp.CustomerServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.serviceorder.utils.Constant.*;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 3:46 PM
 * @created_by thuynt
 * @since 08/07/2020
 */
@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    CustomerServiceImp customerServiceImp;

    @GetMapping("/customer/id/{id}")
    public ResponseEntity<APIResponse<Optional<CustomerDTO>>> findCustomerById(@PathVariable("id") int customerId){
        APIResponse apiResponse = APIResponse.setDataForAPIResponse(customerServiceImp.findCustomerById(customerId), FIND_BY_ID, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/customer/")
    public ResponseEntity<APIResponse<Page<CustomerDTO>>> getAllCustomer(
            @RequestParam(value = "customerName", required = false) String customerName,
           // @RequestParam(value = "status", required = false) int status,
            @RequestParam(value = "customerId", required = false) int customerId,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit){
        Page<CustomerDTO> customerDTOPage = customerServiceImp.getAllCustomer(customerName, customerId, page, limit);
        APIResponse apiResponse = APIResponse.setDataForAPIResponse(customerDTOPage, GET_SUCCESS, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/customer/")
    public ResponseEntity<APIResponse<CustomerDTO>> addCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        APIResponse apiResponse = APIResponse.setDataForAPIResponse(customerServiceImp.addCustomer(customerDTO), SAVE_SUCCESS, HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}

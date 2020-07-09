package com.serviceorder.converts;

import com.serviceorder.dto.CustomerDTO;
import com.serviceorder.entities.Customer;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 11:08 AM
 * @created_by thuynt
 * @since 08/07/2020
 */
public class CustomerConvert {

    public static Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setCustomerPhone(customerDTO.getCustomerPhone());
        customer.setStatus(customerDTO.getStatus());
        customer.setCreatedAt(customerDTO.getCreatedAt());
        customer.setUpdatedAt(customerDTO.getUpdatedAt());

        return customer;
    }

    public static CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setCustomerName(customer.getCustomerName());
        customerDTO.setCustomerPhone(customer.getCustomerPhone());
        customerDTO.setStatus(customer.getStatus());
        customerDTO.setCreatedAt(customer.getCreatedAt());
        customerDTO.setUpdatedAt(customer.getUpdatedAt());

        return customerDTO;

    }
}

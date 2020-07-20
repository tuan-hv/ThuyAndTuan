package com.serviceorder.services;

import com.serviceorder.dto.CustomerDTO;
import com.serviceorder.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 09/07/2020 - 11:30 AM
 * @created_by thuynt
 * @since 09/07/2020
 */
public interface CustomerService {

    Optional<CustomerDTO> findCustomerById(int customerId);
    Page<CustomerDTO> getAllCustomer(String customerName, Integer customerId, int page, int limit);
    CustomerDTO addCustomer(CustomerDTO customerDTO);

}

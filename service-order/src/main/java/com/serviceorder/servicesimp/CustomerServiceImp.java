package com.serviceorder.servicesimp;

import com.serviceorder.converts.CustomerConvert;
import com.serviceorder.dto.CustomerDTO;
import com.serviceorder.entities.Customer;
import com.serviceorder.repositories.CustomerRepository;
import com.serviceorder.repositories.specifications.CustomerSpecification;
import com.serviceorder.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 09/07/2020 - 1:39 PM
 * @created_by thuynt
 * @since 09/07/2020
 */
@Service
public class CustomerServiceImp implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public Optional<CustomerDTO> findCustomerById(int customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            CustomerDTO customerDTO = CustomerConvert.convertCustomerToCustomerDTO(customer.get());
            return Optional.of(customerDTO);
        }
        return Optional.empty();
    }


    @Override
    public Page<CustomerDTO> getAllCustomer(String customerName, Integer customerId, int page, int limit) {
        CustomerSpecification customerSpecification = CustomerSpecification.spec();
        Optional.ofNullable(customerName).ifPresent(s -> customerSpecification.byName(customerName));
        Optional.ofNullable(customerId).ifPresent(s -> customerSpecification.byId(customerId));
        Page<Customer> pageResult = customerRepository.findAll(customerSpecification.build(), PageRequest.of(page - 1, limit));
        return pageResult.map(CustomerConvert::convertCustomerToCustomerDTO);
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerConvert.convertCustomerDTOToCustomer(customerDTO);
        customerRepository.save(customer);
        customerDTO.setCustomerId(customer.getCustomerId());
        return customerDTO;
    }


}

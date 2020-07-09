package com.serviceorder.repositories;

import com.serviceorder.dto.CustomerDTO;
import com.serviceorder.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 1:56 PM
 * @created_by thuynt
 * @since 08/07/2020
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer>, PagingAndSortingRepository<Customer, Integer> {

//    List<CustomerDTO> getAllCustomer(String customerName, int customerId, Pageable pageable);
}

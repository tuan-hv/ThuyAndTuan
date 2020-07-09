package com.serviceorder.repositories;

import com.serviceorder.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 1:56 PM
 * @created_by thuynt
 * @since 08/07/2020
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}

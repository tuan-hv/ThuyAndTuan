package com.serviceorder.repositories.specifications;

import com.serviceorder.entities.AccountType;
import com.serviceorder.entities.Customer;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 09/07/2020 - 1:38 PM
 * @created_by thuynt
 * @since 09/07/2020
 */
public class CustomerSpecification {
    private final List<Specification<Customer>> customerSpecList = new ArrayList<>();

    public static CustomerSpecification spec() {
        return new CustomerSpecification();
    }

    /**
     * Lấy ra Customer có id chỉ định
     * @param customerId
     * @return
     */
    public void byId(Integer customerId){
        customerSpecList.add(hasId(customerId));
    }

    public static Specification<Customer> hasId(Integer customerId) {
        return (root, query, cb) -> cb.equal(root.get("customerId"), customerId);
    }


    /**
     * Lấy ra Customer có name chỉ định
     * @param customerName
     * @return
     */
    public void byName(String customerName){
        customerSpecList.add(hasName(customerName));
    }

    public static Specification<Customer> hasName(String customerName) {
        return (root, query, cb) -> cb.equal(root.get("customerName"), customerName);
    }


    /**
     * Lấy ra type có status chỉ định
     * @param status
     * @return
     */
    public void byStatus(int status){
        customerSpecList.add(hasStatus(status));
    }

    public Specification<Customer> hasStatus(int status){
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }


    private Specification<Customer> all() {
        return (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
    }

    public Specification<Customer> build() {
        return Specification.where(customerSpecList.stream().reduce(all(), Specification::and));
    }

}

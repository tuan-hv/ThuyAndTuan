package com.serviceorder.repositories.specifications;

import com.serviceorder.entities.AccountType;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 4:26 PM
 * @created_by thuynt
 * @since 08/07/2020
 */
public class AccountTypeSpecification {

    private final List<Specification<AccountType>> accountTypeSpecList = new ArrayList<>();


    /**
     * Lấy ra type có id chỉ định
     * @param typeId
     * @return
     */
    public static Specification<AccountType> hasId(int typeId) {
        return (root, query, cb) -> cb.equal(root.get("typeId"), typeId);
    }


    /**
     * Lấy ra type có status chỉ định
     * @param status
     * @return
     */
    public void byStatus(int status){
        accountTypeSpecList.add(hasStatus(status));
    }

    public Specification<AccountType> hasStatus(int status){
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

}

package com.serviceorder.repositories.specifications;

import com.serviceorder.entities.AccountType;
import org.springframework.data.jpa.domain.Specification;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 4:26 PM
 * @created_by thuynt
 * @since 08/07/2020
 */
public class AccountTypeSpecification {

    /**
     * Lấy ra type có id chỉ định
     * @param typeId
     * @return
     */
    public static Specification<AccountType> hasId(int typeId) {
        return (root, query, cb) -> cb.equal(root.get("typeId"), typeId);
    }


}

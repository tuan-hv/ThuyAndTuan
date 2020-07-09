package com.serviceorder.repositories;

import com.serviceorder.entities.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 1:56 PM
 * @created_by thuynt
 * @since 08/07/2020
 */
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer>, JpaSpecificationExecutor<AccountType> {
}

package com.serviceorder.repositories;

import com.serviceorder.entities.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 08/07/2020 - 1:56 PM
 * @created_by thuynt
 * @since 08/07/2020
 */
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer>, JpaSpecificationExecutor<AccountType> {
    Optional<AccountType> findAccountTypeByTypeName(String typeName);

    @Transactional
    @Modifying
    @Query(value = "update AccountType ac set ac.typeName=:name, ac.status=:status where ac.typeId=:typeId")
//    @Query(value = "update type ac set ac.name = typeName, ac.status = status where ac.type_id = 1", nativeQuery = true)
    void updateAccountType(@Param("typeId") int typeId, @Param("name") String name, @Param("status")int status);
}

package com.servicesecutity.repositories;

import com.servicesecutity.entities.Role;
import com.servicesecutity.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);

    @Query(value = "select * from roles r join users u on r.id = u.id where u.username = :username",nativeQuery = true)
    List<Role> findByUser_UserName(@Param("username") String username);
}

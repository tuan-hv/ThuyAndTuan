package com.servicesecutity.servicesecutity.repository;

import com.servicesecutity.servicesecutity.entity.Role;
import com.servicesecutity.servicesecutity.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}

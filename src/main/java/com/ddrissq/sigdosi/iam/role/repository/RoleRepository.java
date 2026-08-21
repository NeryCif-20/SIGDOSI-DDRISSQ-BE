package com.ddrissq.sigdosi.iam.role.repository;

import com.ddrissq.sigdosi.iam.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID>, JpaSpecificationExecutor<Role> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);

}

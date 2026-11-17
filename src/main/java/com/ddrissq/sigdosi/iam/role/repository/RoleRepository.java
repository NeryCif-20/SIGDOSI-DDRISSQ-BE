package com.ddrissq.sigdosi.iam.role.repository;

import com.ddrissq.sigdosi.iam.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID>, JpaSpecificationExecutor<Role> {

    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);

    @Query("""
        SELECT CASE WHEN EXISTS (
            SELECT 1
            FROM User u
            WHERE u.role.id = :id
        ) THEN true ELSE false END
    """)
    boolean existsUsersByRoleId(UUID id);

}

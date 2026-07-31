package com.ddrissq.sigdosi.iam.permission.repository;

import com.ddrissq.sigdosi.iam.permission.model.Permission;
import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID>, JpaSpecificationExecutor<Permission> {

    boolean existsByModuleAndAction(String module, PermissionAction action);
    boolean existsByModuleAndActionAndIdNot(String module, PermissionAction action, UUID id);

}

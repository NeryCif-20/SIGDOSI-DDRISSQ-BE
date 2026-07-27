package com.ddrissq.sigdosi.iam.permission.specification;

import com.ddrissq.sigdosi.iam.permission.model.Permission;
import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import org.springframework.data.jpa.domain.Specification;

public class PermissionSpecification {

    public static Specification<Permission> hasModule(String module) {
        return (root, query, builder) -> {
            if (module == null || module.isBlank()) {
                return null;
            }
            return builder.like(
                    builder.upper(root.get("module")),
                    "%" + module.trim().toUpperCase() + "%");
        };
    }

    public static Specification<Permission> hasAction(PermissionAction action) {
        return (root, query, builder) -> {
            if (action == null) {
                return null;
            }
            return builder.equal(root.get("action"), action);
        };
    }

}

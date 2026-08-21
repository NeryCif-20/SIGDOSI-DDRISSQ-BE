package com.ddrissq.sigdosi.iam.role.specification;

import com.ddrissq.sigdosi.iam.permission.model.Permission;
import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import com.ddrissq.sigdosi.iam.role.model.Role;
import jakarta.persistence.criteria.Join;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoleSpecification {

    public static Specification<Role> hasName(String name) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return builder.like(
                    builder.upper(root.get("name")),
                    "%" + name.trim().toUpperCase() + "%");
        };
    }

    public static Specification<Role> hasModule(String module) {
        return (root, query, builder) -> {
            if (module == null || module.isBlank()) {
                return null;
            }
            query.distinct(true);
            Join<Role, Permission> permissionJoin = root.join("permissions");
            return builder.like(
                    builder.upper(permissionJoin.get("module")),
                    "%" + module.trim().toUpperCase() + "%");
        };
    }

    public static Specification<Role> hasAction(PermissionAction action) {
        return (root, query, builder) -> {
            if (action == null) {
                return null;
            }
            query.distinct(true);
            Join<Role, Permission> permissionJoin = root.join("permissions");
            return builder.equal(permissionJoin.get("action"), action);
        };
    }

}

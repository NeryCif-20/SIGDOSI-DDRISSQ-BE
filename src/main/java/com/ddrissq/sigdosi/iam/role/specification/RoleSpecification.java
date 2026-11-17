package com.ddrissq.sigdosi.iam.role.specification;

import com.ddrissq.sigdosi.iam.permission.model.Permission;
import com.ddrissq.sigdosi.iam.role.model.Role;
import jakarta.persistence.criteria.Join;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoleSpecification {

    public static Specification<Role> hasName(String name) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return builder.like(
                    builder.lower(root.get("name")),
                    "%" + name.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Role> hasPermissions(Collection<UUID> permissions) {
        return (root, query, builder) -> {
            if (permissions == null || permissions.isEmpty()) {
                return null;
            }
            query.distinct(true);
            Join<Role, Permission> permissionJoin = root.join("permissions");
            return permissionJoin.get("id").in(permissions);
        };
    }

}

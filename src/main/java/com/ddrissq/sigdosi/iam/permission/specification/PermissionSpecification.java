package com.ddrissq.sigdosi.iam.permission.specification;

import com.ddrissq.sigdosi.iam.permission.entity.Permission;
import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PermissionSpecification {

    public static Specification<Permission> hasModule(String module) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(module)) {
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

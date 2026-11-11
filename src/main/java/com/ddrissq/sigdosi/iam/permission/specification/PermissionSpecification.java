package com.ddrissq.sigdosi.iam.permission.specification;

import com.ddrissq.sigdosi.iam.permission.model.Permission;
import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PermissionSpecification {

    public static Specification<Permission> hasModule(String module) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(module)) {
                return null;
            }
            return builder.like(
                    builder.lower(root.get("module")),
                    "%" + module.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Permission> hasActions(Collection<PermissionAction> actions) {
        return (root, query, builder) -> {
            if (actions == null || actions.isEmpty()) {
                return null;
            }
            return root.get("action").in(actions);
        };
    }

}

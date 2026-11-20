package com.ddrissq.sigdosi.iam.user.specification;

import com.ddrissq.sigdosi.iam.role.model.Role;
import com.ddrissq.sigdosi.iam.user.model.User;
import com.ddrissq.sigdosi.iam.user.model.UserStatus;
import jakarta.persistence.criteria.Join;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserSpecification {

    public static Specification<User> hasEmail(String email) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(email)) {
                return null;
            }
            return builder.like(
                    builder.lower(root.get("email")),
                    "%" + email.trim().toLowerCase() + "%");
        };
    }

    public static Specification<User> hasStatus(Collection<UserStatus> statuses) {
        return (root, query, builder) -> {
            if (statuses == null || statuses.isEmpty()) {
                return null;
            }
            return root.get("status").in(statuses);
        };
    }

    public static Specification<User> hasCui(String cui) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(cui)) {
                return null;
            }
            return builder.like(
                    root.get("profile").get("cui"),
                    "%" + cui.trim() + "%");
        };
    }

    public static Specification<User> hasName(String name) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            String pattern = "%" + name.trim().toLowerCase() + "%";
            return builder.or(
                    builder.like(
                            builder.lower(root.get("profile").get("firstName")),
                            pattern),
                    builder.like(
                            builder.lower(root.get("profile").get("lastName")),
                            pattern));
        };
    }

    public static Specification<User> hasPhoneNumber(String phoneNumber) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(phoneNumber)) {
                return null;
            }
            return builder.like(
                    root.get("profile").get("phoneNumber"),
                    "%" + phoneNumber.trim() + "%");
        };
    }

    public static Specification<User> hasRole(Collection<UUID> roles) {
        return (root, query, builder) -> {
            if (roles == null || roles.isEmpty()) {
                return null;
            }
            Join<User, Role> roleJoin = root.join("role");
            return roleJoin.get("id").in(roles);
        };
    }

}

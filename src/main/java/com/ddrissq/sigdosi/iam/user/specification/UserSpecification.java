package com.ddrissq.sigdosi.iam.user.specification;

import com.ddrissq.sigdosi.iam.user.model.User;
import com.ddrissq.sigdosi.iam.user.model.UserStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

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

    public static Specification<User> hasStatus(UserStatus status) {
        return (root, query, builder) -> {
            if (status == null) {
                return null;
            }
            return builder.equal(root.get("status"), status);
        };
    }

    public static Specification<User> hasCui(String cui) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(cui)) {
                return null;
            }
            return builder.like(
                    root.get("userProfile").get("cui"),
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
                            builder.lower(root.get("userProfile").get("firstName")),
                            pattern),
                    builder.like(
                            builder.lower(root.get("userProfile").get("lastName")),
                            pattern));
        };
    }

    public static Specification<User> hasPhoneNumber(String phoneNumber) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(phoneNumber)) {
                return null;
            }
            return builder.like(
                    root.get("userProfile").get("phoneNumber"),
                    "%" + phoneNumber.trim() + "%");
        };
    }

}

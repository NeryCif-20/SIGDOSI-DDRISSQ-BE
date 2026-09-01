package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.specification;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.model.PlanType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlanTypeSpecification {

    public static Specification<PlanType> hasCode(String code) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(code)) {
                return null;
            }
            return builder.like(
                    builder.upper(root.get("code")),
                    "%" + code.trim().toUpperCase() + "%");
        };
    }

    public static Specification<PlanType> hasName(String name) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return builder.like(
                    builder.upper(root.get("name")),
                    "%" + name.trim().toUpperCase() + "%");
        };
    }

}

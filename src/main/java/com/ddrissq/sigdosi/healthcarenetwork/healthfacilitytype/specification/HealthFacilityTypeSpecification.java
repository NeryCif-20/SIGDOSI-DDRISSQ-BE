package com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.specification;

import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.model.HealthFacilityType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HealthFacilityTypeSpecification {

    public static Specification<HealthFacilityType> hasCode(String code) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(code)) {
                return null;
            }
            return builder.like(
                    builder.upper(root.get("code")),
                    "%" + code.trim().toUpperCase() + "%");
        };
    }

    public static Specification<HealthFacilityType> hasName(String name) {
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

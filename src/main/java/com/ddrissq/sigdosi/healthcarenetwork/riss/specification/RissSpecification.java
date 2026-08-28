package com.ddrissq.sigdosi.healthcarenetwork.riss.specification;

import com.ddrissq.sigdosi.healthcarenetwork.riss.model.Riss;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RissSpecification {

    public static Specification<Riss> hasName(String name) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return builder.like(
                    builder.upper(root.get("name")),
                    "%" + name.trim().toUpperCase() + "%");
        };
    }

    public static Specification<Riss> hasDmsName(String dmsName) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(dmsName)) {
                return null;
            }
            return builder.like(
                    builder.upper(root.get("dms").get("name")),
                    "%" + dmsName.trim().toUpperCase() + "%");
        };
    }

}

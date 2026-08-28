package com.ddrissq.sigdosi.healthcarenetwork.dms.specification;

import com.ddrissq.sigdosi.healthcarenetwork.dms.model.Dms;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DmsSpecification {

    public static Specification<Dms> hasName(String name) {
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

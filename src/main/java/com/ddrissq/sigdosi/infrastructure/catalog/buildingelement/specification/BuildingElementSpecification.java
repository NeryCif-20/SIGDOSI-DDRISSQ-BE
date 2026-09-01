package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.specification;

import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.model.BuildingElement;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BuildingElementSpecification {

    public static Specification<BuildingElement> hasName(String name) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return builder.like(
                    builder.upper(root.get("name")),
                    "%" + name.trim().toUpperCase() + "%");
        };
    }

    public static Specification<BuildingElement> hasBuildingMaterial(Boolean hasBuildingMaterial) {
        return (root, query, builder) -> {
            if (hasBuildingMaterial == null) {
                return null;
            }
            return builder.equal(
                    root.get("hasBuildingMaterial"),
                    hasBuildingMaterial);
        };
    }

}

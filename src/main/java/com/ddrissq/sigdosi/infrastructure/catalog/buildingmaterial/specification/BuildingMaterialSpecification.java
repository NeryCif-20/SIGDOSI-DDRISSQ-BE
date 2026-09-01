package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.specification;

import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.model.BuildingMaterial;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BuildingMaterialSpecification {

    public static Specification<BuildingMaterial> hasCode(String code) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(code)) {
                return null;
            }
            return builder.like(
                    builder.upper(root.get("name")),
                    "%" + code.trim().toUpperCase() + "%");
        };
    }

    public static Specification<BuildingMaterial> hasName(String name) {
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

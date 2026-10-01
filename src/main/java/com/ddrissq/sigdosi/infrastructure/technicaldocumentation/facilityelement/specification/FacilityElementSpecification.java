package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.specification;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.model.FacilityElement;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FacilityElementSpecification {

    public static Specification<FacilityElement> hasHealthFacility(UUID healthFacility) {
        return (root, query, criteriaBuilder) -> {
            if (healthFacility == null) {
                return null;
            }
            return criteriaBuilder.equal(
                    root.get("healthFacility").get("id"),
                    healthFacility);
        };
    }

    public static Specification<FacilityElement> hasBuildingMaterial(UUID buildingMaterial) {
        return (root, query, builder) -> {
            if (buildingMaterial == null) {
                return null;
            }
            return builder.equal(
                    root.get("buildingMaterial").get("id"),
                    buildingMaterial);
        };
    }

    public static Specification<FacilityElement> hasBuildingElementName(String buildingElementName) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(buildingElementName)) {
                return null;
            }
            return builder.like(
                    builder.lower(root.get("buildingElement").get("name")),
                    "%" + buildingElementName.trim().toLowerCase() + "%");
        };
    }

}

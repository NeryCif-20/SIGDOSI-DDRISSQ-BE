package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.specifcation;

import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.model.Plan;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlanSpecification {

    public static Specification<Plan> hasType(UUID type) {
        return (root, query, builder) -> {
            if (type == null) {
                return null;
            }
            return builder.equal(
                    root.get("type").get("id"),
                    type);
        };
    }

    public static Specification<Plan> hasHealthFacility(UUID healthFacility) {
        return (root, query, criteriaBuilder) -> {
            if (healthFacility == null) {
                return null;
            }
            return criteriaBuilder.equal(
                    root.get("healthFacility").get("id"),
                    healthFacility);
        };
    }

    public static Specification<Plan> hasVersion(Short version) {
        return (root, query, criteriaBuilder) -> {
            if (version == null) {
                return null;
            }
            return criteriaBuilder.equal(
                    root.get("version"),
                    version);
        };
    }

}

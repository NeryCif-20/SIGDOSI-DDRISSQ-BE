package com.ddrissq.sigdosi.healthcarenetwork.community.specification;

import com.ddrissq.sigdosi.healthcarenetwork.community.model.Community;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommunitySpecification {

    public static Specification<Community> hasName(String name) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return builder.like(
                    builder.lower(root.get("name")),
                    "%" + name.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Community> hasRissName(String name) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return builder.like(
                    builder.lower(root.get("riss").get("name")),
                    "%" + name.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Community> hasDmsName(String name) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return builder.like(
                    builder.lower(root.get("riss").get("dms").get("name")),
                    "%" + name.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Community> hasTerritory(Short territory) {
        return (root, query, builder) -> {
            if (territory == null) {
                return null;
            }
            return builder.equal(
                    root.get("territory"), territory);
        };
    }

    public static Specification<Community> hasSector(String sector) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(sector)) {
                return null;
            }
            return builder.equal(
                    builder.lower(root.get("sector")),
                    sector.trim().toLowerCase());
        };
    }

    public static Specification<Community> populationBetween(Long minPopulation, Long maxPopulation) {
        return (root, query, builder) -> {
            if (minPopulation == null && maxPopulation == null) {
                return null;
            }
            if (minPopulation != null && maxPopulation != null) {
                return builder.between(
                        root.get("population"),
                        minPopulation,
                        maxPopulation);
            }
            if (minPopulation != null) {
                return builder.greaterThanOrEqualTo(
                        root.get("population"), minPopulation);
            }
            return builder.lessThanOrEqualTo(
                    root.get("population"), maxPopulation);
        };
    }

}

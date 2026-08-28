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
                    builder.upper(root.get("riss").get("name")),
                    "%" + name.trim().toUpperCase() + "%");
        };
    }

    public static Specification<Community> hasDmsName(String name) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return builder.like(
                    builder.upper(root.get("riss").get("dms").get("name")),
                    "%" + name.trim().toUpperCase() + "%");
        };
    }

    public static Specification<Community> hasTerritory(Integer territory) {
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
            if (StringUtils.hasText(sector)) {
                return null;
            }
            return builder.equal(
                    builder.upper(root.get("sector")),
                    sector.trim().toUpperCase());
        };
    }

    public static Specification<Community> populationBetween(Long min, Long max) {
        return (root, query, criteriaBuilder) -> {
            if (min != null && max != null) {
                return criteriaBuilder.between(
                        root.get("population"), min, max);
            }
            if (max != null) {
                return criteriaBuilder.lessThanOrEqualTo(
                        root.get("population"), max);
            }
            if (min != null) {
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get("population"), min);
            }
            return null;
        };
    }

}

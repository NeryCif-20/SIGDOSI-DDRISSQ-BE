package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.specification;

import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacilityStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyStatus;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.PropertyTenure;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HealthFacilitySpecification {

    public static Specification<HealthFacility> hasCommunity(UUID community) {
        return (root, query, builder) -> {
            if (community == null) {
                return null;
            }
            return builder.equal(root.get("community").get("id"), community);
        };
    }

    public static Specification<HealthFacility> hasType(UUID type) {
        return (root, query, builder) -> {
            if (type == null) {
                return null;
            }
            return builder.equal(root.get("type").get("id"), type);
        };
    }

    public static Specification<HealthFacility> isHeadquarters(Boolean isHeadquarters) {
        return (root, query, builder) -> {
            if (isHeadquarters == null) {
                return null;
            }
            return builder.equal(root.get("isHeadquarters"), isHeadquarters);
        };
    }

    public static Specification<HealthFacility> hasStatus(HealthFacilityStatus status) {
        return (root, query, builder) -> {
            if (status == null) {
                return null;
            }
            return builder.equal(root.get("status"), status);
        };
    }

    public static Specification<HealthFacility> hasPropertyTenure(PropertyTenure propertyTenure) {
        return (root, query, builder) -> {
            if (propertyTenure == null) {
                return null;
            }
            return builder.equal(root.get("propertyTenure"), propertyTenure);
        };
    }

    public static Specification<HealthFacility> hasPropertyStatus(PropertyStatus propertyStatus) {
        return (root, query, builder) -> {
            if (propertyStatus == null) {
                return null;
            }
            return builder.equal(root.get("propertyStatus"), propertyStatus);
        };
    }

    public static Specification<HealthFacility> hasTotalLandAreaBetween(
            BigDecimal minTotalLandArea,
            BigDecimal maxTotalLandArea) {
        return (root, query, builder) -> {
            if (minTotalLandArea == null && maxTotalLandArea == null) {
                return null;
            }
            if (minTotalLandArea != null && maxTotalLandArea != null) {
                return builder.between(
                        root.get("totalLandArea"),
                        minTotalLandArea,
                        maxTotalLandArea);
            }
            if (minTotalLandArea != null) {
                return builder.greaterThanOrEqualTo(
                        root.get("totalLandArea"), minTotalLandArea);
            }
            return builder.lessThanOrEqualTo(
                    root.get("totalLandArea"), maxTotalLandArea);
        };
    }

    public static Specification<HealthFacility> hasBuildingFootprintBetween(
            BigDecimal minBuildingFootprint,
            BigDecimal maxBuildingFootprint) {
        return (root, query, builder) -> {
            if (minBuildingFootprint == null && maxBuildingFootprint == null) {
                return null;
            }
            if (minBuildingFootprint != null && maxBuildingFootprint != null) {
                return builder.between(
                        root.get("buildingFootprint"),
                        minBuildingFootprint,
                        maxBuildingFootprint);
            }
            if (minBuildingFootprint != null) {
                return builder.greaterThanOrEqualTo(
                        root.get("buildingFootprint"), minBuildingFootprint);
            }
            return builder.lessThanOrEqualTo(
                    root.get("buildingFootprint"), maxBuildingFootprint);
        };
    }

    public static Specification<HealthFacility> hasAvailableExpansionAreaBetween(
            BigDecimal minAvailableExpansionArea,
            BigDecimal maxAvailableExpansionArea) {
        return (root, query, builder) -> {
            if (minAvailableExpansionArea == null && maxAvailableExpansionArea == null) {
                return null;
            }
            if (minAvailableExpansionArea != null && maxAvailableExpansionArea != null) {
                return builder.between(
                        root.get("availableExpansionArea"),
                        minAvailableExpansionArea,
                        maxAvailableExpansionArea);
            }
            if (minAvailableExpansionArea != null) {
                return builder.greaterThanOrEqualTo(
                        root.get("availableExpansionArea"), minAvailableExpansionArea);
            }
            return builder.lessThanOrEqualTo(
                    root.get("availableExpansionArea"), maxAvailableExpansionArea);
        };
    }

}

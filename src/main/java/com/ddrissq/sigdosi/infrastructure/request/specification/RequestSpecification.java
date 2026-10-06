package com.ddrissq.sigdosi.infrastructure.request.specification;

import com.ddrissq.sigdosi.infrastructure.request.model.Request;
import com.ddrissq.sigdosi.infrastructure.request.model.RequestStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestSpecification {

    public static Specification<Request> hasReferenceCode(String referenceCode) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(referenceCode)) {
                return null;
            }
            return builder.like(
                    builder.lower(root.get("referenceCode")),
                    "%" + referenceCode.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Request> hasCommunityName(String communityName) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(communityName)) {
                return null;
            }
            return builder.like(
                    builder.lower(root.get("healthFacility").get("community").get("name")),
                    "%" + communityName.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Request> hasRissName(String rissName) {
        return (root, query, builder) -> {
            if (!StringUtils.hasText(rissName)) {
                return null;
            }
            return builder.like(builder.lower(
                    root.get("healthFacility").get("community").get("riss").get("name")),
                    "%" + rissName.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Request> hasStatus(RequestStatus status) {
        return (root, query, builder) -> {
            if (status == null) {
                return null;
            }
            return builder.equal(root.get("status"), status);
        };
    }

    public static Specification<Request> aproximateCostBetween(BigDecimal minAproximateCost, BigDecimal maxAproximateCost) {
        return (root, query, builder) -> {
            if (minAproximateCost == null && maxAproximateCost == null) {
                return null;
            }
            if (minAproximateCost != null && maxAproximateCost != null) {
                return builder.between(
                        root.get("aproximateCost"),
                        minAproximateCost,
                        maxAproximateCost);
            }
            if (minAproximateCost != null) {
                return builder.greaterThanOrEqualTo(
                        root.get("aproximateCost"), minAproximateCost);
            }
            return builder.lessThanOrEqualTo(
                    root.get("aproximateCost"), maxAproximateCost);
        };
    }

    public static Specification<Request> requestedDateBetween(LocalDate minRequestedDate, LocalDate maxRequestedDate) {
        return (root, query, builder) -> {
            if (minRequestedDate == null && maxRequestedDate == null) {
                return null;
            }
            if (minRequestedDate != null && maxRequestedDate != null) {
                return builder.between(
                        root.get("requestedDate"),
                        minRequestedDate,
                        maxRequestedDate);
            }
            if (minRequestedDate != null) {
                return builder.greaterThanOrEqualTo(
                        root.get("requestedDate"), minRequestedDate);
            }
            return builder.lessThanOrEqualTo(
                    root.get("requestedDate"), maxRequestedDate);
        };
    }

}

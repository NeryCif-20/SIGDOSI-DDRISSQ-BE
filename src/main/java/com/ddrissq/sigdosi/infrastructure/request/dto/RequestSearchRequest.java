package com.ddrissq.sigdosi.infrastructure.request.dto;

import com.ddrissq.sigdosi.common.validation.annotation.CompareFields;
import com.ddrissq.sigdosi.common.validation.annotation.ComparisonOperator;
import com.ddrissq.sigdosi.infrastructure.request.model.RequestStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@CompareFields(first = "minAproximateCost", second = "maxAproximateCost", operator = ComparisonOperator.LESS_THAN_OR_EQUAL)
@CompareFields(first = "minRequestedDate", second = "maxRequestedDate", operator = ComparisonOperator.LESS_THAN_OR_EQUAL)
@Builder
public record RequestSearchRequest(
        String q,
        RequestStatus status,
        BigDecimal minAproximateCost,
        BigDecimal maxAproximateCost,
        LocalDate minRequestedDate,
        LocalDate maxRequestedDate
) {
}

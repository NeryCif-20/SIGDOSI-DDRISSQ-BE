package com.ddrissq.sigdosi.infrastructure.request.dto;

import com.ddrissq.sigdosi.infrastructure.request.model.RequestStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record RequestResponse(
        UUID id,
        String referenceCode,
        String requesterName,
        String requesterEmail,
        String requesterPhoneNumber,
        String requesterOfficialPosition,
        String requesterFunctionalPosition,
        Short roomsToExpand,
        BigDecimal aproximateCost,
        String notes,
        RequestStatus status,
        LocalDate requestedDate
) {
}

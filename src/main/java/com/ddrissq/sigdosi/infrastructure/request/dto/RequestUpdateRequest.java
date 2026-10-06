package com.ddrissq.sigdosi.infrastructure.request.dto;

import com.ddrissq.sigdosi.common.validation.annotation.NullableNotBlank;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.infrastructure.request.model.RequestStatus;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record RequestUpdateRequest(
        @NullableNotBlank
        @Size(min = 1, max = 100, message = ValidationError.SIZE)
        String requesterName,
        @NullableNotBlank
        @Size(min = 1, max = 100, message = ValidationError.SIZE)
        String requesterEmail,
        @Pattern(regexp = "^\\d{8}$", message = ValidationError.PHONE_NUMBER_PATTERN)
        String requesterPhoneNumber,
        @NullableNotBlank
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String requesterOfficialPosition,
        @NullableNotBlank
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String requesterFunctionalPosition,
        @PositiveOrZero(message = ValidationError.POSITIVE_OR_ZERO)
        Short roomsToExpand,
        @PositiveOrZero(message = ValidationError.POSITIVE_OR_ZERO)
        BigDecimal aproximateCost,
        String notes,
        RequestStatus status,
        @PastOrPresent(message = ValidationError.PAST_OR_PRESENT)
        LocalDate requestedDate
) {
}

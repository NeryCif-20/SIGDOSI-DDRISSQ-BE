package com.ddrissq.sigdosi.infrastructure.request.dto;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.infrastructure.request.model.RequestStatus;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record RequestCreateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        UUID healthFacility,
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 100, message = ValidationError.SIZE)
        String requesterName,
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 100, message = ValidationError.SIZE)
        String requesterEmail,
        @NotBlank(message = ValidationError.REQUIRED)
        @Pattern(regexp = "^\\d{8}$", message = ValidationError.PHONE_NUMBER_PATTERN)
        String requesterPhoneNumber,
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String requesterOfficialPosition,
        @NotBlank(message = ValidationError.REQUIRED)
        @Size(min = 1, max = 50, message = ValidationError.SIZE)
        String requesterFunctionalPosition,
        @NotNull(message = ValidationError.REQUIRED)
        @PositiveOrZero(message = ValidationError.POSITIVE_OR_ZERO)
        Short roomsToExpand,
        @NotNull(message = ValidationError.REQUIRED)
        @PositiveOrZero(message = ValidationError.POSITIVE_OR_ZERO)
        BigDecimal aproximateCost,
        String notes,
        @NotNull(message = ValidationError.REQUIRED)
        RequestStatus status,
        @NotNull(message = ValidationError.REQUIRED)
        @PastOrPresent(message = ValidationError.PAST_OR_PRESENT)
        LocalDate requestedDate
) {
}

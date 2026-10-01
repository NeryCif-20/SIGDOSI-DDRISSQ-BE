package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto;

import com.ddrissq.sigdosi.common.file.validation.annotation.AllowedFileMimeTypes;
import com.ddrissq.sigdosi.common.file.validation.annotation.MaxFileSize;
import com.ddrissq.sigdosi.common.file.validation.annotation.NotEmptyFile;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record PlanCreateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        UUID type,
        @NotNull(message = ValidationError.REQUIRED)
        UUID healthFacility,
        @NotNull(message = ValidationError.REQUIRED)
        @NotEmptyFile
        @AllowedFileMimeTypes(value = {"image/jpeg", "image/png", "image/svg+xml", "application/pdf"})
        @MaxFileSize
        MultipartFile file,
        @NotNull(message = ValidationError.REQUIRED)
        @Positive(message = ValidationError.POSITIVE)
        Short version,
        String notes
) {
}

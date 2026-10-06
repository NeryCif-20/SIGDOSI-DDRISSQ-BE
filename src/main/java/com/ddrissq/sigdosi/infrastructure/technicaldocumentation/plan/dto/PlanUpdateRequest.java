package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.dto;

import com.ddrissq.sigdosi.common.file.validation.annotation.AllowedFileMimeTypes;
import com.ddrissq.sigdosi.common.file.validation.annotation.MaxFileSize;
import com.ddrissq.sigdosi.common.file.validation.annotation.NotEmptyFile;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Builder
public record PlanUpdateRequest(
        UUID type,
        @NotEmptyFile
        @AllowedFileMimeTypes(value = {"image/jpeg", "image/png", "image/svg+xml", "application/pdf"})
        @MaxFileSize
        MultipartFile file,
        @Positive(message = ValidationError.POSITIVE)
        Short version,
        String notes
) {
}

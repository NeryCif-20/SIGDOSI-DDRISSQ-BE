package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.dto;

import com.ddrissq.sigdosi.common.file.validation.annotation.AllowedFileMimeTypes;
import com.ddrissq.sigdosi.common.file.validation.annotation.MaxFileSize;
import com.ddrissq.sigdosi.common.file.validation.annotation.NotEmptyFile;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record HealthFacilityImageCreateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        @NotEmptyFile
        @AllowedFileMimeTypes(value = {"image/jpeg", "image/png", "image/webp"})
        @MaxFileSize
        MultipartFile image
) {
}

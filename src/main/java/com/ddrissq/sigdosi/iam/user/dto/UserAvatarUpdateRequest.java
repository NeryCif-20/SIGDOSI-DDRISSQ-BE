package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.common.file.validation.annotation.NotEmptyFile;
import com.ddrissq.sigdosi.common.file.validation.annotation.AllowedFileMimeTypes;
import com.ddrissq.sigdosi.common.file.validation.annotation.MaxFileSize;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record UserAvatarUpdateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        @NotEmptyFile
        @AllowedFileMimeTypes(value = {"image/jpeg", "image/png", "image/webp"})
        @MaxFileSize(value = "3MB")
        MultipartFile avatar
) {
}
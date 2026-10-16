package com.ddrissq.sigdosi.infrastructure.request.attachment.dto;

import com.ddrissq.sigdosi.common.file.validation.annotation.AllowedFileMimeTypes;
import com.ddrissq.sigdosi.common.file.validation.annotation.MaxFileSize;
import com.ddrissq.sigdosi.common.file.validation.annotation.NotEmptyFile;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record RequestAttachmentCreateRequest(
        @NotNull(message = ValidationError.REQUIRED)
        @NotEmptyFile
        @AllowedFileMimeTypes(value = {"image/jpeg", "image/png", "image/webp", "image/svg+xml", "application/pdf"})
        @MaxFileSize
        MultipartFile attachment
) {
}

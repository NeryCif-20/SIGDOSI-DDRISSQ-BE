package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.common.validation.file.annotation.FileMetadata;
import com.ddrissq.sigdosi.common.validation.file.annotation.FileContentType;
import com.ddrissq.sigdosi.common.validation.file.annotation.MaxFileSize;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record UserAvatarUpdateRequest(
        @NotNull
        @FileMetadata
        @FileContentType(allowed = {"image/jpeg", "image/png", "image/webp"})
        @MaxFileSize(value = "2MB")
        MultipartFile avatar
) {
}
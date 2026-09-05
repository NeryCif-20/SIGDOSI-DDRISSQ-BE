package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.common.validation.annotation.WellFormedFile;
import com.ddrissq.sigdosi.common.validation.annotation.FileContentType;
import com.ddrissq.sigdosi.common.validation.annotation.MaxFileSize;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record UserAvatarUpdateRequest(
        @NotNull(message = "El avatar es obligatorio")
        @WellFormedFile(message = "El archivo debe ser valido")
        @FileContentType(
                message = "El avatar debe ser una imagen valida",
                allowed = {"image/jpeg", "image/png", "image/webp"})
        @MaxFileSize(
                value = "2MB",
                message = "El tamaño de la imagen no debe superar los {value}")
        MultipartFile avatar
) {
}
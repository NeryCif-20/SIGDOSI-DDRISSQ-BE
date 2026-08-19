package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.common.validation.annotation.RequiredFile;
import com.ddrissq.sigdosi.common.validation.annotation.ImageFile;
import com.ddrissq.sigdosi.common.validation.annotation.MaxFileSize;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record UserAvatarUpdateRequest(
        @RequiredFile(message = "El archivo es obligatorio")
        @ImageFile(message = "El avatar debe ser una imagen valida")
        @MaxFileSize(
                value = "2MB",
                message = "El tamaño de la imagen no debe superar los {value}")
        MultipartFile avatar
) {
}
package com.ddrissq.sigdosi.iam.user.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserCreateRequest(
        @NotNull(message = "El rol es obligatorio")
        UUID role,
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato valido")
        String email,
        @NotBlank(message = "El CUI es obligatorio")
        @Pattern(regexp = "^\\d{13}$", message = "El CUI debe ser de 13 dígitos")
        String cui,
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El nombre no debe exceder los {max} caracteres")
        String firstName,
        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 50, message = "El apellido no debe exceder los {max} caracteres")
        String lastName,
        @Pattern(regexp = "^\\d{8}$", message = "El número de teléfono debe ser de 8 dígitos")
        String phoneNumber
) {
}

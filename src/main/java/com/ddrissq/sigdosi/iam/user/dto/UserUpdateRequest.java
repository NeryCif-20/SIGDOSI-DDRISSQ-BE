package com.ddrissq.sigdosi.iam.user.dto;

import com.ddrissq.sigdosi.iam.user.model.UserAccountStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserUpdateRequest(
        @Email(message = "El email no tiene un formato valido")
        String email,
        UserAccountStatus status,
        @Pattern(regexp = "^\\d{13}$", message = "El CUI debe ser de 13 dígitos")
        String cui,
        @Size(max = 50, message = "El nombre no debe exceder los {max} caracteres")
        String firstName,
        @Size(max = 50, message = "El apellido no debe exceder los {max} caracteres")
        String lastName,
        @Pattern(regexp = "^\\d{8}$", message = "El número de teléfono debe ser de 8 dígitos")
        String phoneNumber
) {
}

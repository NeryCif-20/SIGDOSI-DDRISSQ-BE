package com.ddrissq.sigdosi.healthcarenetwork.community.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record CommunityCreateRequest(
        @NotNull(message = "La riss es obligatoria")
        UUID riss,
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El tamaño maximo del nombre es de {max} caracteres")
        String name,
        @NotNull(message = "El territorio es obligatorio")
        @Positive(message = "El territorio debe ser un numero positivo")
        Integer territory,
        @NotBlank(message = "El sector es obligatorio")
        @Pattern(regexp = "^[A-Za-z]$", message = "El sector solo puede ser una letra")
        String sector,
        @NotNull(message = "La población es obligatoria")
        @Positive(message = "La poblacion debe ser un numero positivo")
        Long population
) {
}

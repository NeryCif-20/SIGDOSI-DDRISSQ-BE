package com.ddrissq.sigdosi.healthcarenetwork.community.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record CommunityCreateRequest(
        @NotNull(message = "La riss es obligatoria")
        UUID riss,
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotNull(message = "El territorio es obligatorio")
        @Min(value = 0, message = "El territorio no puede ser menor a {value}")
        Integer territory,
        @NotBlank(message = "El sector es obligatorio")
        @Pattern(regexp = "^[A-Za-z]$", message = "El sector solo puede ser una letra")
        String sector,
        @NotNull(message = "La población es obligatoria")
        @Min(value = 0, message = "La población no puede ser menor a {value}")
        Long population
) {
}

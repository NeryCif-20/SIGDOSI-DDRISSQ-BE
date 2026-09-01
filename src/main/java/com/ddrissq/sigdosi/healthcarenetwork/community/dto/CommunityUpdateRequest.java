package com.ddrissq.sigdosi.healthcarenetwork.community.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CommunityUpdateRequest(
        UUID riss,
        @Size(max = 50, message = "El tamaño maximo del nombre es de {max} caracteres")
        String name,
        Integer territory,
        @Pattern(regexp = "^[A-Za-z]$", message = "El sector solo puede ser una letra")
        String sector,
        Long population
) {
}

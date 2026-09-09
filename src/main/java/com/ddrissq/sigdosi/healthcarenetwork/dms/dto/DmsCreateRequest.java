package com.ddrissq.sigdosi.healthcarenetwork.dms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DmsCreateRequest(
        @NotBlank
        @Size(max = 50)
        String name
) {
}

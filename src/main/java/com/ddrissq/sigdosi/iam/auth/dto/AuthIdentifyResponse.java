package com.ddrissq.sigdosi.iam.auth.dto;

import com.ddrissq.sigdosi.iam.auth.model.AuthNextStep;
import lombok.Builder;

@Builder
public record AuthIdentifyResponse(
        AuthNextStep nextStep
) {
}

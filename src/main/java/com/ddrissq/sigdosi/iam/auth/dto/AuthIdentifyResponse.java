package com.ddrissq.sigdosi.iam.auth.dto;

import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowStep;
import lombok.Builder;

@Builder
public record AuthIdentifyResponse(
        FlowStep step
) {
}

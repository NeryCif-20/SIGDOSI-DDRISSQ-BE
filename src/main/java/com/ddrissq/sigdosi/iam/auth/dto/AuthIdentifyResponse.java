package com.ddrissq.sigdosi.iam.auth.dto;

import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowTokenStep;
import lombok.Builder;

@Builder
public record AuthIdentifyResponse(
        FlowTokenStep step
) {
}

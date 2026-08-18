package com.ddrissq.sigdosi.iam.auth.model;

import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowStep;
import lombok.Builder;

@Builder
public record AuthIdentityResult(
        String flowToken,
        FlowStep step
) {
}

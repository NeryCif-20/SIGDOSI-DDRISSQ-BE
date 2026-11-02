package com.ddrissq.sigdosi.iam.auth.model;

import lombok.Builder;

@Builder
public record PasswordSetupRequired(
) implements AuthIdentifyResult {

    @Override
    public AuthNextStep nextStep() {
        return AuthNextStep.CHECK_MAIL;
    }

}

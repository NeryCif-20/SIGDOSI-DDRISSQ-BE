package com.ddrissq.sigdosi.iam.auth.model;

public sealed interface AuthIdentifyResult permits PasswordSetupRequired, PasswordRequired {

    public AuthNextStep nextStep();

}

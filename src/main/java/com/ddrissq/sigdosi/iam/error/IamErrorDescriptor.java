package com.ddrissq.sigdosi.iam.error;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.error.ErrorTitle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum IamErrorDescriptor implements ErrorDescriptor {

    AUTHENTICATION_DISABLED("error.authentication.disabled", ErrorTitle.AUTHENTICATION),
    AUTHENTICATION_REQUIRED("error.authentication.required", ErrorTitle.AUTHENTICATION),
    CREDENTIALS_INVALID("error.authentication.credentials.invalid", ErrorTitle.AUTHENTICATION),
    TOKEN_EXPIRED("error.authentication.token.expired", ErrorTitle.AUTHENTICATION),
    TOKEN_INVALID("error.authentication.token.invalid", ErrorTitle.AUTHENTICATION),
    ACCESS_DENIED("error.authorization.access.denied", ErrorTitle.AUTHORIZATION);

    private final String messageKey;
    private final ErrorTitle errorTitle;

    @Override
    public String code() {
        return this.name();
    }

    @Override
    public String messageKey() {
        return this.messageKey;
    }

    @Override
    public String titleKey() {
        return this.errorTitle.key();
    }
}

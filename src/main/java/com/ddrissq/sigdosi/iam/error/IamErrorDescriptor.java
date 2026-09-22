package com.ddrissq.sigdosi.iam.error;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.error.ErrorTitle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum IamErrorDescriptor implements ErrorDescriptor {

    AUTHENTICATION_DISABLED("error.authentication.disabled", ErrorTitle.AUTHENTICATION),
    AUTHENTICATION_REQUIRED("error.authentication.required", ErrorTitle.AUTHENTICATION),
    AUTHENTICATION_CREDENTIALS_INVALID("error.authentication.credentials.invalid", ErrorTitle.AUTHENTICATION),
    AUTHENTICATION_TOKEN_EXPIRED("error.authentication.token.expired", ErrorTitle.AUTHENTICATION),
    AUTHENTICATION_TOKEN_INVALID("error.authentication.token.invalid", ErrorTitle.AUTHENTICATION),
    AUTHORIZATION_FORBIDDEN("error.authorization.forbidden", ErrorTitle.AUTHORIZATION);

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

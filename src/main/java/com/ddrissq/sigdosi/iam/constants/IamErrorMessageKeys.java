package com.ddrissq.sigdosi.iam.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IamErrorMessageKeys {

    public static final String AUTHENTICATION_DISABLED = "iam.authentication.disabled";
    public static final String AUTHENTICATION_REQUIRED = "iam.authentication.required";
    public static final String AUTHENTICATION_CREDENTIALS_INVALID = "iam.authentication.credentials.invalid";
    public static final String TOKEN_EXPIRED = "iam.token.expired";
    public static final String TOKEN_INVALID = "iam.token.invalid";
    public static final String ACCESS_DENIED = "iam.access.denied";

}

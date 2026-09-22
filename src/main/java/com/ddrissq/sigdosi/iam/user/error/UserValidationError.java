package com.ddrissq.sigdosi.iam.user.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserValidationError {

    public static final String CUI_PATTERN = "{error.user.cui.pattern}";
    public static final String PHONE_NUMBER_PATTERN = "{error.user.phone.pattern}";

}

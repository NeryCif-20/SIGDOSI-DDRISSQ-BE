package com.ddrissq.sigdosi.iam.user.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserErrorMessageKeys {

    public static final String NOT_FOUND = "user.not.found";
    public static final String EMAIL_ALREADY_EXISTS = "user.email.already.exists";
    public static final String CUI_ALREADY_EXISTS = "user.cui.already.exists";
    public static final String PHONE_NUMBER_ALREADY_EXISTS = "user.phone.number.already.exists";

}

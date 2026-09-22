package com.ddrissq.sigdosi.iam.user.error;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.error.ErrorTitle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorDescriptor implements ErrorDescriptor {

    NOT_FOUND("error.user.not-found", ErrorTitle.RESOURCE_NOT_FOUND),
    EMAIL_ALREADY_EXISTS("error.user.email.already-exists", ErrorTitle.RESOURCE_ALREADY_EXISTS),
    CUI_ALREADY_EXISTS("error.user.cui.already-exists", ErrorTitle.RESOURCE_ALREADY_EXISTS),
    PHONE_NUMBER_ALREADY_EXISTS("error.user.phone-number.already-exists", ErrorTitle.RESOURCE_ALREADY_EXISTS),
    STATUS_NOT_ALLOWED("error.user.status.not-allowed", ErrorTitle.BUSINESS_RULE);

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

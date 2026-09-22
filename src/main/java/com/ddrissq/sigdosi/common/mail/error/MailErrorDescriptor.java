package com.ddrissq.sigdosi.common.mail.error;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.error.ErrorTitle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MailErrorDescriptor implements ErrorDescriptor {

    SEND_FAILED("error.mail.send.failed");

    private static final ErrorTitle ERROR_TITLE = ErrorTitle.MAIL;

    private final String messageKey;

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
        return ERROR_TITLE.key();
    }
}

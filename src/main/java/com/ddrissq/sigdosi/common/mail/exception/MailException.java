package com.ddrissq.sigdosi.common.mail.exception;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.exception.BaseException;

public class MailException extends BaseException {
    public MailException(ErrorDescriptor descriptor) {
        super(descriptor);
    }
}

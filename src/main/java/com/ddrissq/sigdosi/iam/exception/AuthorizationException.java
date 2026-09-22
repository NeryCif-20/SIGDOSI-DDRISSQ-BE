package com.ddrissq.sigdosi.iam.exception;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.exception.BaseException;

public class AuthorizationException extends BaseException {

    public AuthorizationException(ErrorDescriptor descriptor) {
        super(descriptor);
    }

}

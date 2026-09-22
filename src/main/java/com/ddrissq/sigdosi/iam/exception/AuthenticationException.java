package com.ddrissq.sigdosi.iam.exception;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.exception.BaseException;

public class AuthenticationException extends BaseException {

    public AuthenticationException(ErrorDescriptor descriptor) {
        super(descriptor);
    }

}

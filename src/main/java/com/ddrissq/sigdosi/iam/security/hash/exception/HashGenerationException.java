package com.ddrissq.sigdosi.iam.security.hash.exception;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.exception.BaseException;

public class HashGenerationException extends BaseException {

    public HashGenerationException(ErrorDescriptor descriptor, Object... args) {
        super(descriptor, args);
    }

    public HashGenerationException(ErrorDescriptor descriptor) {
        super(descriptor);
    }

}


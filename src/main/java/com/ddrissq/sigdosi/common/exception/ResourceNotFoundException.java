package com.ddrissq.sigdosi.common.exception;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(ErrorDescriptor descriptor) {
        super(descriptor);
    }

}

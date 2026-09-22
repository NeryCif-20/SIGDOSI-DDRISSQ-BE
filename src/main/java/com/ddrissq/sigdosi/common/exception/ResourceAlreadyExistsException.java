package com.ddrissq.sigdosi.common.exception;


import com.ddrissq.sigdosi.common.error.ErrorDescriptor;

public class ResourceAlreadyExistsException extends BaseException {

    public ResourceAlreadyExistsException(ErrorDescriptor descriptor) {
        super(descriptor);
    }

}

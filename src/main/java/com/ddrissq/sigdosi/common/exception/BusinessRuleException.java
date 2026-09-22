package com.ddrissq.sigdosi.common.exception;


import com.ddrissq.sigdosi.common.error.ErrorDescriptor;

public class BusinessRuleException extends BaseException {

    public BusinessRuleException(ErrorDescriptor descriptor, Object... arguments) {
        super(descriptor, arguments);
    }

}

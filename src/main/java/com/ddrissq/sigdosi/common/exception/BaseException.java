package com.ddrissq.sigdosi.common.exception;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private final String title;
    private final String code;
    private final Object[] arguments;

    public BaseException(ErrorDescriptor errorDescriptor, Object... arguments) {
        super(errorDescriptor.messageKey());
        this.title = errorDescriptor.titleKey();
        this.code = errorDescriptor.code();
        this.arguments = arguments;
    }

}

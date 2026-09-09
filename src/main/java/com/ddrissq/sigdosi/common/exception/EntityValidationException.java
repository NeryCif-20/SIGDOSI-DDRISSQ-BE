package com.ddrissq.sigdosi.common.exception;

import lombok.Getter;

@Getter
public class EntityValidationException extends RuntimeException {

    private Object[] args;

    public EntityValidationException(String message) {
        super(message);
    }

    public EntityValidationException(String message, Object... args) {
        this.args = args;
        super(message);
    }

}

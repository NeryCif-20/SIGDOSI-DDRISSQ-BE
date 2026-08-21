package com.ddrissq.sigdosi.common.exception;

import com.ddrissq.sigdosi.common.constant.ErrorMessages;

public class InvalidStateTransitionException extends RuntimeException {

    public InvalidStateTransitionException(Object currentState, Object newState) {
        String message = String.format(
                ErrorMessages.INVALID_STATE_TRANSITION,
                currentState,
                newState);
        super(message);
    }

}

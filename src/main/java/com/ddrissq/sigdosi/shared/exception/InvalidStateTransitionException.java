package com.ddrissq.sigdosi.shared.exception;

public class InvalidStateTransitionException extends RuntimeException {

    public InvalidStateTransitionException(Object currentState, Object newState) {
        String message = String.format(
                ExceptionMessages.INVALID_STATE_TRANSITION,
                currentState,
                newState);
        super(message);
    }

}

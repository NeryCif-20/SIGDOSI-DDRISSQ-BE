package com.ddrissq.sigdosi.common.validation.error;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ValidationErrorDescriptor implements ErrorDescriptor {

    UNSUPPORTED_TYPES("error.validation.types.unsupported");

    private final String messageKey;

    @Override
    public String code() {
        return this.name();
    }

    @Override
    public String messageKey() {
        return this.messageKey;
    }

    @Override
    public String titleKey() {
        return null;
    }

}

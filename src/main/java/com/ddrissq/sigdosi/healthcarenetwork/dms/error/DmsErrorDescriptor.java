package com.ddrissq.sigdosi.healthcarenetwork.dms.error;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.error.ErrorTitle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DmsErrorDescriptor implements ErrorDescriptor {

    NOT_FOUND("error.dms.not-found", ErrorTitle.RESOURCE_NOT_FOUND),
    ALREADY_EXISTS("error.dms.already-exists", ErrorTitle.RESOURCE_ALREADY_EXISTS);

    private final String messageKey;
    private final ErrorTitle errorTitle;

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
        return this.errorTitle.key();
    }

}

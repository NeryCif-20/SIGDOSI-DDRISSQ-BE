package com.ddrissq.sigdosi.healthcarenetwork.community.error;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.error.ErrorTitle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommunityErrorDescriptor implements ErrorDescriptor {

    NOT_FOUND("error.community.not-found", ErrorTitle.RESOURCE_NOT_FOUND),
    ALREADY_EXISTS("error.community.already-exists", ErrorTitle.RESOURCE_ALREADY_EXISTS);

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

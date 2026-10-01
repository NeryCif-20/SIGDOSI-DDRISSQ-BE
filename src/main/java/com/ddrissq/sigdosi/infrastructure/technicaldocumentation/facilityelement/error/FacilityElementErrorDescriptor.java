package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.error;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.error.ErrorTitle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FacilityElementErrorDescriptor implements ErrorDescriptor {

    NOT_FOUND("error.facility-element.not-found", ErrorTitle.RESOURCE_NOT_FOUND),
    ALREADY_EXISTS("error.facility-element.already-exists", ErrorTitle.RESOURCE_ALREADY_EXISTS),
    MATERIAL_MISSING("error.facility-element.material.missing", ErrorTitle.BUSINESS_RULE);

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

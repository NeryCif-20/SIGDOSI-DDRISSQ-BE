package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.error;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.error.ErrorTitle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HealthFacilityImageErrorDescriptor implements ErrorDescriptor {

    LIMIT_EXCEEDED("error.health-facility.images.limit.exceeded", ErrorTitle.BUSINESS_RULE);

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

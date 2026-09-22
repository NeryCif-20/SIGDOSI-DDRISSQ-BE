package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.error;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.error.ErrorTitle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HealthFacilityErrorDescriptor implements ErrorDescriptor {

    NOT_FOUND("error.health-facility.not-found", ErrorTitle.RESOURCE_NOT_FOUND),
    TOTAL_LAND_AREA_EXCEEDED("error.health-facility.total-land-area.exceeded", ErrorTitle.BUSINESS_RULE);

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

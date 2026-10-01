package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "health-facilities.images")
public record HealthFacilityImageProperties(
        Integer limit
) {

    public HealthFacilityImageProperties {
        if (limit == null) {
            limit = 15;
        }
    }

}

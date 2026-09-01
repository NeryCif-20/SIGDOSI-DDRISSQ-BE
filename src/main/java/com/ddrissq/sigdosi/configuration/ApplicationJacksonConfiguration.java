package com.ddrissq.sigdosi.configuration;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationJacksonConfiguration {

    @Bean
    public JtsModule jtsModule() {
        GeometryFactory geometryFactory = new GeometryFactory(
                new PrecisionModel(), 4326);
        return new JtsModule(geometryFactory);
    }

}

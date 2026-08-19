package com.ddrissq.sigdosi.common.configuration.application;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

    private Client client;

    @Getter
    @Setter
    public static class Client {
        String origin;
    }

}

package com.ddrissq.sigdosi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "testcontainers")
public class TestcontainersProperties {

    private Container postgres;
    private Container redis;

    @Getter
    @Setter
    public static class Container {
        private String version;
    }

}

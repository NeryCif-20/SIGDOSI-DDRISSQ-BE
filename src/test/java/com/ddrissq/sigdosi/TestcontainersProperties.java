package com.ddrissq.sigdosi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "testcontainers")
@Component
public class TestcontainersProperties {

    private Container postgres;
    private Container redis;

    @Getter
    @Setter
    public static class Container {
        private String version;
    }

}

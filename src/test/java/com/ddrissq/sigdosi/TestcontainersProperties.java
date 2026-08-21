package com.ddrissq.sigdosi;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "testcontainers")
public record TestcontainersProperties(
       ContainerProperties postgres
) {

    public record ContainerProperties(
            String version
    ) {
    }

}

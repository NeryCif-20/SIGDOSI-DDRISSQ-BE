package com.ddrissq.sigdosi.iam.auth.mail.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.mail")
public record AuthMailProperties(
        String setupPasswordPath
) {
}

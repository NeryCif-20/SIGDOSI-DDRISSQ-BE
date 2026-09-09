package com.ddrissq.sigdosi.iam.auth.mail.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "iam.auth.mail")
public record AuthMailProperties(
        Paths paths
) {

    public record Paths(
            String setupPassword,
            String resetPassword
    ) {
    }

}

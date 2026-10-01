package com.ddrissq.sigdosi.iam.auth.mail.configuration;

import com.ddrissq.sigdosi.iam.auth.model.PasswordSetupAction;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "auth.mail")
public record AuthMailProperties(
        Map<PasswordSetupAction, String> setPasswordPaths
) {
}

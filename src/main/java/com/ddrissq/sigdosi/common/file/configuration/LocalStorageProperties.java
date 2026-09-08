package com.ddrissq.sigdosi.common.file.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "app.files.storage.local")
public record LocalStorageProperties(
        Path path
) {
}

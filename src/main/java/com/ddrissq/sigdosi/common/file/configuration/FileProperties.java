package com.ddrissq.sigdosi.common.file.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.files")
public record FileProperties(
        StorageProperties storage
) {

    public record StorageProperties(
            StorageType type
    ) {

        public enum StorageType {
            LOCAL,
            S3
        }

    }

}

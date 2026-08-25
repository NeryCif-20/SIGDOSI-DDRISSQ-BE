package com.ddrissq.sigdosi.common.file.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.files.storage.s3")
public record S3StorageProperties(
        String bucketName
) {
}

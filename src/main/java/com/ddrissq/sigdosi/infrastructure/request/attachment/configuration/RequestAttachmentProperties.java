package com.ddrissq.sigdosi.infrastructure.request.attachment.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "requests.attachments")
public record RequestAttachmentProperties(
        Integer limit
) {

    public RequestAttachmentProperties {
        if (limit == null) {
            limit = 10;
        }
    }

}

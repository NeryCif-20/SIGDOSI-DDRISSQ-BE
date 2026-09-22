package com.ddrissq.sigdosi.common.file.storage.model;

import lombok.Builder;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

@Builder
public record FileLoadResult(
        Resource resource,
        MediaType contentType
) {
}

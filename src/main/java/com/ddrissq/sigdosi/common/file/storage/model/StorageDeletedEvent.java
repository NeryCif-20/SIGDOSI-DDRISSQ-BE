package com.ddrissq.sigdosi.common.file.storage.model;

import lombok.Builder;
import lombok.Singular;

import java.util.Collection;

@Builder
public record StorageDeletedEvent(
        @Singular
        Collection<String> paths
) {
}

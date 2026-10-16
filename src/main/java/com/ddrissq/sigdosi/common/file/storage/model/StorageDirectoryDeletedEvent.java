package com.ddrissq.sigdosi.common.file.storage.model;

import lombok.Builder;

@Builder
public record StorageDirectoryDeletedEvent(
        StoragePath path
) {
}

package com.ddrissq.sigdosi.common.file.storage.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StorageFolder {
    AVATARS("avatars"),
    HEALTH_FACILITIES("health-facilities"),
    PLANS("plans");

    private final String path;

}

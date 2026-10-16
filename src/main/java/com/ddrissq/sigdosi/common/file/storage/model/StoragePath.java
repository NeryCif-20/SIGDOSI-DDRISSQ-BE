package com.ddrissq.sigdosi.common.file.storage.model;

import com.ddrissq.sigdosi.common.file.storage.error.StorageErrorDescriptor;
import com.ddrissq.sigdosi.common.file.storage.exception.StorageOperationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoragePath {

    private static final Pattern RESOURCE_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");
    private static final Pattern FILENAME_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]+$");

    private final List<String> segments = new ArrayList<>();

    public static StoragePath forResource(StorageResource resource, String resourceId) {
        StoragePath storagePathSegment = new StoragePath();
        storagePathSegment.subResource(resource, resourceId);
        return storagePathSegment;
    }

    public static StoragePath forResource(StorageResource resource, UUID resourceId) {
        return forResource(resource, resourceId.toString());
    }

    public StoragePath subResource(StorageResource resource, String resourceId) {
        if (resource == null || !StringUtils.hasText(resource.name())) {
            throw new StorageOperationException(StorageErrorDescriptor.SAVE_FAILED);
        }
        if (!StringUtils.hasText(resourceId) || !RESOURCE_ID_PATTERN.matcher(resource.name()).matches()) {
            throw new StorageOperationException(StorageErrorDescriptor.SAVE_FAILED);
        }
        this.segments.add(resource.name());
        this.segments.add(resourceId);
        return this;
    }

    public StoragePath subResource(StorageResource resource, UUID resourceId) {
        return subResource(resource, resourceId.toString());
    }

    public String buildPath() {
        return String.join("/", this.segments);
    }

}

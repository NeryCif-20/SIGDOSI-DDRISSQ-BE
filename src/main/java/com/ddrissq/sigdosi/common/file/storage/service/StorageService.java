package com.ddrissq.sigdosi.common.file.storage.service;

import com.ddrissq.sigdosi.common.file.storage.model.StorageDeletedEvent;
import com.ddrissq.sigdosi.common.file.storage.model.StorageDirectoryDeletedEvent;
import com.ddrissq.sigdosi.common.file.storage.model.StoragePath;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String save(MultipartFile file, StoragePath path);
    void delete(StorageDeletedEvent event);
    void deleteDirectory(StorageDirectoryDeletedEvent event);

}

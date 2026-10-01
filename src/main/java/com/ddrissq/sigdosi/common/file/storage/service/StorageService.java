package com.ddrissq.sigdosi.common.file.storage.service;

import com.ddrissq.sigdosi.common.file.storage.model.StorageFolder;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String save(MultipartFile file);
    String save(MultipartFile file, StorageFolder folder);
    void delete(String... paths);

}

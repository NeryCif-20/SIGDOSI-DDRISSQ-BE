package com.ddrissq.sigdosi.common.file.storage.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String save(MultipartFile file);
    void delete(String filename);

}

package com.ddrissq.sigdosi.common.file.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileStorage {

    String save(MultipartFile file);
    void delete(String filename);

}

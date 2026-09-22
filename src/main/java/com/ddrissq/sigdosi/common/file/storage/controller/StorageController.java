package com.ddrissq.sigdosi.common.file.storage.controller;

import com.ddrissq.sigdosi.common.file.storage.model.FileLoadResult;
import com.ddrissq.sigdosi.common.file.storage.service.StorageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.files.storage.type", havingValue = "local")
@RequestMapping(path = "/v1/files")
@RestController
public class StorageController {

    private final StorageServiceImpl storage;

    @GetMapping(path = "/{filename}")
    public ResponseEntity<Resource> load(
            @PathVariable String filename) {
        FileLoadResult result = storage.load(filename);
        String content = ContentDisposition
                .inline()
                .filename(filename)
                .build().toString();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(result.contentType())
                .header(HttpHeaders.CONTENT_DISPOSITION, content)
                .body(result.resource());
    }

}

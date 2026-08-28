package com.ddrissq.sigdosi.common.file.controller;

import com.ddrissq.sigdosi.common.file.storage.FileStorageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.files.storage.type", havingValue = "local")
@RequestMapping(path = "/v1/files")
@RestController
public class FileController {

    private final FileStorageImpl storage;

    @GetMapping(path = "/{filename}")
    public ResponseEntity<Resource> load(
            @PathVariable String filename) {
        Resource resource = storage.load(filename);
        MediaType contentType = MediaTypeFactory.getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(contentType)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + filename + "\"")
                .body(resource);
    }

}

package com.ddrissq.sigdosi.common.file.storage.service;

import com.ddrissq.sigdosi.common.exception.ResourceNotFoundException;
import com.ddrissq.sigdosi.common.file.storage.configuration.LocalStorageProperties;
import com.ddrissq.sigdosi.common.file.storage.error.StorageErrorDescriptor;
import com.ddrissq.sigdosi.common.file.storage.exception.StorageException;
import com.ddrissq.sigdosi.common.file.storage.model.FileLoadResult;
import com.ddrissq.sigdosi.common.file.util.FileAnalyzer;
import com.ddrissq.sigdosi.common.util.UuidGenerator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.files.storage.type", havingValue = "local")
@Component
public class StorageServiceImpl implements StorageService {

    private final LocalStorageProperties props;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(getPath());
        } catch (IOException ex) {
            throw new StorageException(
                    StorageErrorDescriptor.INITIALIZATION_FAILED);
        }
    }

    @Override
    public String save(MultipartFile file) {
        try {
            String extension = FileAnalyzer.getExtension(file);
            String filename = UuidGenerator.generateV7() + extension;
            Path destinationFile = getPath().resolve(filename)
                    .normalize();
            Files.copy(
                    file.getInputStream(),
                    destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException | MimeTypeException ex) {
            throw new StorageException(
                    StorageErrorDescriptor.SAVE_FAILED);
        }
    }

    @Override
    public void delete(String filename) {
        Path file = getPath().resolve(filename)
                .normalize();
        try {
            Files.deleteIfExists(file);
        } catch (IOException ex) {
            throw new StorageException(
                    StorageErrorDescriptor.DELETE_FAILED);
        }
    }

    public FileLoadResult load(String filename) {
        Path file = getPath().resolve(filename)
                .normalize();
        Resource resource = new FileSystemResource(file);
        if (!resource.exists()) {
            throw new ResourceNotFoundException(
                    StorageErrorDescriptor.NOT_FOUND);
        }
        if (!resource.isReadable()) {
            throw new StorageException(
                    StorageErrorDescriptor.LOAD_FAILED);
        }
        try {
            MediaType contentType = MediaType.parseMediaType(FileAnalyzer.getMimeType(resource));
            return FileLoadResult.builder()
                    .resource(resource)
                    .contentType(contentType)
                    .build();
        } catch (IOException | MimeTypeException ex) {
            throw new StorageException(
                    StorageErrorDescriptor.LOAD_FAILED);
        }
    }

    private Path getPath() {
        return props.path().toAbsolutePath().normalize();
    }

}

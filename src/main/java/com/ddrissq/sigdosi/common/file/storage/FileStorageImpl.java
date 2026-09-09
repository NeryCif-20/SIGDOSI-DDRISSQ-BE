package com.ddrissq.sigdosi.common.file.storage;

import com.ddrissq.sigdosi.common.file.configuration.LocalStorageProperties;
import com.ddrissq.sigdosi.common.file.constant.FileErrorMessageKeys;
import com.ddrissq.sigdosi.common.file.exception.FileNotFoundException;
import com.ddrissq.sigdosi.common.file.exception.FileStorageException;
import com.ddrissq.sigdosi.common.file.exception.InvalidFileException;
import com.ddrissq.sigdosi.common.file.util.FileAnalyzer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.files.storage.type", havingValue = "local")
@Component
public class FileStorageImpl implements FileStorage {

    private final LocalStorageProperties props;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(getPath());
        } catch (IOException ex) {
            throw new FileStorageException(
                    FileErrorMessageKeys.STORAGE_INITIALIZATION_FAILED);
        }
    }

    @Override
    public String save(MultipartFile file) {
        try {
            String extension = FileAnalyzer.detectExtension(file);
            String filename = UUID.randomUUID() + extension;
            Path destinationFile = getPath().resolve(filename)
                    .normalize();
            Files.copy(
                    file.getInputStream(),
                    destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException | MimeTypeException ex) {
            throw new FileStorageException(
                    FileErrorMessageKeys.STORAGE_FAILED);
        }
    }

    @Override
    public void delete(String filename) {
        Path file = getPath().resolve(filename)
                .normalize();
        try {
            Files.deleteIfExists(file);
        } catch (IOException ex) {
            throw new FileStorageException(
                    FileErrorMessageKeys.DELETE_FAILED);
        }
    }

    public Resource load(String filename) {
        Path file = getPath().resolve(filename)
                .normalize();
        Resource resource = new FileSystemResource(file);
        if (!resource.exists()) {
            throw new FileNotFoundException(
                    FileErrorMessageKeys.NOT_FOUND);
        }
        if (!resource.isReadable()) {
            throw new InvalidFileException(
                    FileErrorMessageKeys.LOAD_FAILED);
        }
        return resource;
    }

    private Path getPath() {
        return props.path().toAbsolutePath().normalize();
    }

}

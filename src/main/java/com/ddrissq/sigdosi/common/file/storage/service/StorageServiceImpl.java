package com.ddrissq.sigdosi.common.file.storage.service;

import com.ddrissq.sigdosi.common.exception.ResourceNotFoundException;
import com.ddrissq.sigdosi.common.file.storage.configuration.LocalStorageProperties;
import com.ddrissq.sigdosi.common.file.storage.error.StorageErrorDescriptor;
import com.ddrissq.sigdosi.common.file.storage.exception.InternalStorageException;
import com.ddrissq.sigdosi.common.file.storage.exception.StorageOperationException;
import com.ddrissq.sigdosi.common.file.storage.model.FileLoadResult;
import com.ddrissq.sigdosi.common.file.storage.model.StorageDeletedEvent;
import com.ddrissq.sigdosi.common.file.storage.model.StorageDirectoryDeletedEvent;
import com.ddrissq.sigdosi.common.file.storage.model.StoragePath;
import com.ddrissq.sigdosi.common.file.util.FileAnalyzer;
import com.ddrissq.sigdosi.common.util.UuidGenerator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
            throw new StorageOperationException(
                    StorageErrorDescriptor.INITIALIZATION_FAILED);
        }
    }

    @Override
    public String save(MultipartFile file, StoragePath path) {
        try {
            String extension = FileAnalyzer.getExtension(file);
            String filename = UuidGenerator.generateV7() + extension;
            Path targetDirectory = getPath().resolve(path.buildPath()).normalize();
            Files.createDirectories(targetDirectory);
            Path targetLocation = targetDirectory
                    .resolve(filename).normalize();
            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING);
            return getPath().relativize(targetLocation)
                    .toString()
                    .replace("\\", "/");
        } catch (IOException | MimeTypeException ex) {
            throw new StorageOperationException(
                    StorageErrorDescriptor.SAVE_FAILED);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async(value = "fileAsyncExecutor")
    @Retryable(
            includes = InternalStorageException.class,
            maxRetries = 4,
            delay = 60_000,
            multiplier = 2)
    @Override
    public void delete(StorageDeletedEvent event) {
        List<String> undeletedFiles = new ArrayList<>();
        for (String path : event.paths()) {
            Path file = getPath().resolve(path)
                    .normalize();
            try {
                Files.deleteIfExists(file);
            } catch (IOException ex) {
                log.error("Failed to delete file '{}'.", path, ex);
                undeletedFiles.add(path);
            }
        }
        if (!undeletedFiles.isEmpty()) {
            throw new InternalStorageException(
                    "Failed to delete file(s): {%s}".formatted(
                            String.join(", ", undeletedFiles)));
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async(value = "fileAsyncExecutor")
    @Retryable(
            includes = InternalStorageException.class,
            maxRetries = 4,
            delay = 60_000,
            multiplier = 2)
    @Override
    public void deleteDirectory(StorageDirectoryDeletedEvent event) {
        Path directory = getPath().resolve(event.path().buildPath()).normalize();
        if (Files.notExists(directory)) {
            return;
        }
        try {
            FileSystemUtils.deleteRecursively(directory);
        } catch (IOException ex) {
            log.error("Failed to delete directory '{}'.", directory);
            throw new InternalStorageException(
                    "Failed to delete directory '%s'.".formatted(directory.toString()), ex);
        }
    }

    public FileLoadResult load(String path) {
        Path file = getPath().resolve(path)
                .normalize();
        Resource resource = new FileSystemResource(file);
        if (!resource.exists()) {
            throw new ResourceNotFoundException(
                    StorageErrorDescriptor.NOT_FOUND);
        }
        if (!resource.isReadable()) {
            throw new StorageOperationException(
                    StorageErrorDescriptor.LOAD_FAILED);
        }
        try {
            MediaType contentType = MediaType.parseMediaType(FileAnalyzer.getMimeType(resource));
            return FileLoadResult.builder()
                    .resource(resource)
                    .contentType(contentType)
                    .build();
        } catch (IOException | MimeTypeException ex) {
            throw new StorageOperationException(
                    StorageErrorDescriptor.LOAD_FAILED);
        }
    }

    private Path getPath() {
        return props.path().toAbsolutePath().normalize();
    }

}

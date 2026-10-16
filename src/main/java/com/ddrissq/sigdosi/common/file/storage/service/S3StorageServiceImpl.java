package com.ddrissq.sigdosi.common.file.storage.service;

import com.ddrissq.sigdosi.common.file.storage.configuration.S3StorageProperties;
import com.ddrissq.sigdosi.common.file.storage.error.StorageErrorDescriptor;
import com.ddrissq.sigdosi.common.file.storage.exception.InternalStorageException;
import com.ddrissq.sigdosi.common.file.storage.exception.StorageOperationException;
import com.ddrissq.sigdosi.common.file.storage.model.StorageDeletedEvent;
import com.ddrissq.sigdosi.common.file.storage.model.StorageDirectoryDeletedEvent;
import com.ddrissq.sigdosi.common.file.storage.model.StoragePath;
import com.ddrissq.sigdosi.common.file.util.FileAnalyzer;
import com.ddrissq.sigdosi.common.util.UuidGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.files.storage.type", havingValue = "s3")
@Component
public class S3StorageServiceImpl implements StorageService {

    private final S3Client s3Client;
    private final S3StorageProperties props;

    @Override
    public String save(MultipartFile file, StoragePath path) {
        try {
            String extension = FileAnalyzer.getExtension(file);
            String contentType = FileAnalyzer.getMimeType(file);
            String filename = UuidGenerator.generateV7() + extension;
            String key = String.join("/", path.buildPath(), filename);
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(getBucket())
                    .key(key)
                    .contentType(contentType)
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
            return key;
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
        List<ObjectIdentifier> keys = event.paths().stream()
                .map(path ->
                    ObjectIdentifier.builder()
                            .key(path)
                            .build())
                .toList();
        DeleteObjectsResponse response = deleteObjects(keys);
        if (response.hasErrors()) {
            List<String> undeletedFiles = response.errors().stream()
                    .map(S3Error::key)
                    .toList();
            undeletedFiles.forEach(filename -> {
                log.error("Failed to delete file '{}'.", filename);
            });
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
        String basePath = event.path().buildPath();
        String prefix = basePath.endsWith("/") ? basePath : basePath + "/";
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(getBucket())
                .prefix(prefix)
                .build();
        s3Client.listObjectsV2Paginator(request).forEach(page -> {
            List<S3Object> objects = page.contents();
            if (!objects.isEmpty()) {
                List<ObjectIdentifier> keys = objects.stream()
                        .map(object -> ObjectIdentifier.builder()
                                .key(object.key())
                                .build())
                        .toList();
                DeleteObjectsResponse response = deleteObjects(keys);
                if (response.hasErrors()) {
                    log.error("Failed to delete directory '{}'.", prefix);
                    throw new InternalStorageException(
                            "Failed to delete directory '%s'.".formatted(prefix));
                }
            }
        });
    }

    private DeleteObjectsResponse deleteObjects(List<ObjectIdentifier> keys) {
        Delete delete = Delete.builder()
                .objects(keys)
                .quiet(true)
                .build();
        DeleteObjectsRequest request = DeleteObjectsRequest.builder()
                .bucket(getBucket())
                .delete(delete)
                .build();
        return s3Client.deleteObjects(request);
    }

    private String getBucket() {
        return props.bucketName();
    }

}

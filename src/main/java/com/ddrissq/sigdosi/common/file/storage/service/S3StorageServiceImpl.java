package com.ddrissq.sigdosi.common.file.storage.service;

import com.ddrissq.sigdosi.common.file.storage.configuration.S3StorageProperties;
import com.ddrissq.sigdosi.common.file.storage.error.StorageErrorDescriptor;
import com.ddrissq.sigdosi.common.file.storage.exception.InternalStorageException;
import com.ddrissq.sigdosi.common.file.storage.exception.StorageOperationException;
import com.ddrissq.sigdosi.common.file.storage.model.StorageFolder;
import com.ddrissq.sigdosi.common.file.util.FileAnalyzer;
import com.ddrissq.sigdosi.common.util.UuidGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.files.storage.type", havingValue = "s3")
@Component
public class S3StorageServiceImpl implements StorageService {

    private final S3Client s3Client;
    private final S3StorageProperties props;

    @Override
    public String save(MultipartFile file) {
        return save(file, null);
    }

    @Override
    public String save(MultipartFile file, StorageFolder folder) {
        try {
            String extension = FileAnalyzer.getExtension(file);
            String filename = UuidGenerator.generateV7() + extension;
            String contentType = FileAnalyzer.getMimeType(file);
            String key = (folder == null || folder.getPath().isEmpty())
                    ? filename
                    : folder.getPath() + "/" + filename;
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
    @Async(value = "imageAsyncExecutor")
    @Retryable(
            retryFor = InternalStorageException.class,
            maxAttempts = 4,
            backoff = @Backoff(delay = 60_000, multiplier = 2))
    @Override
    public void delete(String... paths) {
        List<ObjectIdentifier> keys = Arrays.stream(paths)
                .map(path -> {
                    return ObjectIdentifier.builder()
                            .key(path)
                            .build();
                })
                .toList();
        Delete delete = Delete.builder()
                .objects(keys)
                .quiet(true)
                .build();
        DeleteObjectsRequest request = DeleteObjectsRequest.builder()
                .bucket(getBucket())
                .delete(delete)
                .build();
        DeleteObjectsResponse response = s3Client.deleteObjects(request);
        if (response.hasErrors()) {
            List<String> undeletedFiles = response.errors().stream()
                    .map(S3Error::key)
                    .toList();
            undeletedFiles.forEach(filename -> {
                log.error("Failed to delete file '{}'.", filename);
            });
            throw new InternalStorageException(
                    "Failed to delete " + undeletedFiles.size() + " file(s).",
                    undeletedFiles);
        }
    }

    @Recover
    public void recoverDelete(InternalStorageException ex, String... paths) {
        for (String filename : ex.getUndeletedFiles()) {
            log.error("Failed to delete file '{}' after all retry attempts.", filename);
        }
        log.error(
                "Failed to delete {} files after all retry attempts.",
                ex.getUndeletedFiles().size(),
                ex);
    }

    private String getBucket() {
        return props.bucketName();
    }

}

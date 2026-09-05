package com.ddrissq.sigdosi.common.file.storage;

import com.ddrissq.sigdosi.common.file.configuration.S3StorageProperties;
import com.ddrissq.sigdosi.common.file.constant.FileErrorMessages;
import com.ddrissq.sigdosi.common.file.exception.FileStorageException;
import com.ddrissq.sigdosi.common.file.util.FileAnalyzer;
import lombok.RequiredArgsConstructor;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.files.storage.type", havingValue = "s3")
@Component
public class S3FileStorageImpl implements FileStorage {

    private final S3Client s3Client;
    private final S3StorageProperties props;

    @Override
    public String save(MultipartFile file) {
        try {
            String extension = FileAnalyzer.detectExtension(file);
            String filename = UUID.randomUUID() + extension;
            String contentType = FileAnalyzer.detectContentType(file);
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(getBucket())
                    .key(filename)
                    .contentType(contentType)
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
            return filename;
        } catch (IOException | MimeTypeException ex) {
            throw new FileStorageException(
                    FileErrorMessages.FILE_STORAGE_FAILED);
        }
    }

    @Override
    public void delete(String filename) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(getBucket())
                .key(filename)
                .build();
        s3Client.deleteObject(request);
    }

    private String getBucket() {
        return props.bucketName();
    }

}

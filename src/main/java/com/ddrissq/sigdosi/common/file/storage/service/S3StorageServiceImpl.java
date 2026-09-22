package com.ddrissq.sigdosi.common.file.storage.service;

import com.ddrissq.sigdosi.common.file.storage.configuration.S3StorageProperties;
import com.ddrissq.sigdosi.common.file.storage.error.StorageErrorDescriptor;
import com.ddrissq.sigdosi.common.file.storage.exception.StorageException;
import com.ddrissq.sigdosi.common.file.util.FileAnalyzer;
import com.ddrissq.sigdosi.common.util.UuidGenerator;
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

@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.files.storage.type", havingValue = "s3")
@Component
public class S3StorageServiceImpl implements StorageService {

    private final S3Client s3Client;
    private final S3StorageProperties props;

    @Override
    public String save(MultipartFile file) {
        try {
            String extension = FileAnalyzer.getExtension(file);
            String filename = UuidGenerator.generateV7() + extension;
            String contentType = FileAnalyzer.getMimeType(file);
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(getBucket())
                    .key(filename)
                    .contentType(contentType)
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
            return filename;
        } catch (IOException | MimeTypeException ex) {
            throw new StorageException(
                    StorageErrorDescriptor.SAVE_FAILED);
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

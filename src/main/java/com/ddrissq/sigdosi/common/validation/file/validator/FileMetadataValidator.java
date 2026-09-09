package com.ddrissq.sigdosi.common.validation.file.validator;

import com.ddrissq.sigdosi.common.validation.file.annotation.FileMetadata;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileMetadataValidator implements ConstraintValidator<FileMetadata, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value.isEmpty()) {
            return false;
        }
        String fileName = value.getOriginalFilename();
        if (!StringUtils.hasText(fileName)) {
            return false;
        }
        String extension = StringUtils.getFilenameExtension(fileName);
        return StringUtils.hasText(extension);
    }
}

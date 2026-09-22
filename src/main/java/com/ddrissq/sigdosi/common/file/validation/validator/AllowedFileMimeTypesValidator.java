package com.ddrissq.sigdosi.common.file.validation.validator;

import com.ddrissq.sigdosi.common.file.util.FileAnalyzer;
import com.ddrissq.sigdosi.common.file.validation.annotation.AllowedFileMimeTypes;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

public class AllowedFileMimeTypesValidator implements ConstraintValidator<AllowedFileMimeTypes, MultipartFile> {

    private String[] value;

    @Override
    public void initialize(AllowedFileMimeTypes annotation) {
        this.value = annotation.value();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        try {
            if (file == null || file.isEmpty()) {
                return true;
            }
            String mimeType = FileAnalyzer.getMimeType(file);
            return Arrays.asList(this.value).contains(mimeType);
        } catch (IOException | MimeTypeException ex) {
            return false;
        }
    }

}

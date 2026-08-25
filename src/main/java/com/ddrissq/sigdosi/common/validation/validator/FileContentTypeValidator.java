package com.ddrissq.sigdosi.common.validation.validator;

import com.ddrissq.sigdosi.common.file.util.FileAnalyzer;
import com.ddrissq.sigdosi.common.validation.annotation.FileContentType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private static final Tika TIKA = new Tika();

    private String[] allowed;

    @Override
    public void initialize(FileContentType annotation) {
        this.allowed = annotation.allowed();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        try {
            String detectedType = FileAnalyzer.detectContentType(value);
            return Arrays.asList(allowed).contains(detectedType);
        } catch (IOException ex) {
            return false;
        }
    }

}

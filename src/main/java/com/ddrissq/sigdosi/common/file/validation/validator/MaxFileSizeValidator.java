package com.ddrissq.sigdosi.common.file.validation.validator;

import com.ddrissq.sigdosi.common.file.validation.annotation.MaxFileSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class MaxFileSizeValidator implements ConstraintValidator<MaxFileSize, MultipartFile> {

    private long value;

    @Override
    public void initialize(MaxFileSize annotation) {
        this.value = DataSize.parse(annotation.value())
                .toBytes();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }
        return file.getSize() <= this.value;
    }
}

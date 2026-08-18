package com.ddrissq.sigdosi.shared.validation.validator;

import com.ddrissq.sigdosi.shared.validation.annotation.RequiredFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class RequiredFileValidator implements ConstraintValidator<RequiredFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value != null && !value.isEmpty();
    }
}

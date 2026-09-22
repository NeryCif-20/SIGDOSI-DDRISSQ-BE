package com.ddrissq.sigdosi.common.file.validation.annotation;

import com.ddrissq.sigdosi.common.file.validation.error.FileValidationError;
import com.ddrissq.sigdosi.common.file.validation.validator.MaxFileSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MaxFileSizeValidator.class)
public @interface MaxFileSize {

    String message() default FileValidationError.MAX_SIZE;

    String value() default "5MB";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

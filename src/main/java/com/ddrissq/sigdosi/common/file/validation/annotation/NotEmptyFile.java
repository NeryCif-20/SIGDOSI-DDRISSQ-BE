package com.ddrissq.sigdosi.common.file.validation.annotation;

import com.ddrissq.sigdosi.common.file.validation.error.FileValidationError;
import com.ddrissq.sigdosi.common.file.validation.validator.NotEmptyFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT, ElementType.TYPE_USE })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NotEmptyFileValidator.class)
public @interface NotEmptyFile {

    String message() default FileValidationError.NOT_EMPTY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

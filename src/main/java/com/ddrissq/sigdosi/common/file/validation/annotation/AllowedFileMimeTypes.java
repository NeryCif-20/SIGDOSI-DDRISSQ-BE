package com.ddrissq.sigdosi.common.file.validation.annotation;

import com.ddrissq.sigdosi.common.file.validation.error.FileValidationError;
import com.ddrissq.sigdosi.common.file.validation.validator.AllowedFileMimeTypesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT, ElementType.TYPE_USE })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AllowedFileMimeTypesValidator.class)
public @interface AllowedFileMimeTypes {

    String message() default FileValidationError.ALLOWED_MIME_TYPES;

    String[] value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

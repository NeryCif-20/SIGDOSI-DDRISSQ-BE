package com.ddrissq.sigdosi.common.validation.file.annotation;

import com.ddrissq.sigdosi.common.validation.file.constant.FileErrorMessageKeys;
import com.ddrissq.sigdosi.common.validation.file.validator.FileContentTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FileContentTypeValidator.class)
public @interface FileContentType {

    String message() default FileErrorMessageKeys.FILE_CONTENT_TYPE_INVALID;

    String[] allowed();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

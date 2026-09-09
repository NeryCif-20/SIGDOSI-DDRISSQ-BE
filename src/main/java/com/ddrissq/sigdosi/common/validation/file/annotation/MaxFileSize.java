package com.ddrissq.sigdosi.common.validation.file.annotation;

import com.ddrissq.sigdosi.common.validation.file.constant.FileErrorMessageKeys;
import com.ddrissq.sigdosi.common.validation.file.validator.MaxFileSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MaxFileSizeValidator.class)
public @interface MaxFileSize {

    String message() default FileErrorMessageKeys.FILE_SIZE_EXCEEDED;

    String value() default "5MB";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

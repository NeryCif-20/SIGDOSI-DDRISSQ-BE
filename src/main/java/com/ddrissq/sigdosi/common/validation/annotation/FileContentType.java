package com.ddrissq.sigdosi.common.validation.annotation;

import com.ddrissq.sigdosi.common.validation.validator.FileContentTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FileContentTypeValidator.class)
public @interface FileContentType {

    String message() default "file must be a valid image";

    String[] allowed();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

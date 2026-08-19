package com.ddrissq.sigdosi.common.validation.annotation;

import com.ddrissq.sigdosi.common.validation.validator.MaxFileSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MaxFileSizeValidator.class)
public @interface MaxFileSize {

    String message() default "file exceeds the maximum allowed size";

    String value() default "5MB";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

package com.ddrissq.sigdosi.shared.validation.annotation;

import com.ddrissq.sigdosi.shared.validation.validator.RequiredFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RequiredFileValidator.class)
public @interface RequiredFile {

    String message() default "file cannot be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

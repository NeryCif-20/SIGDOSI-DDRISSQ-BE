package com.ddrissq.sigdosi.common.validation.wellformedfile.annotation;

import com.ddrissq.sigdosi.common.validation.wellformedfile.validator.WellFormedFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = WellFormedFileValidator.class)
public @interface WellFormedFile {

    String message() default "file cannot be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

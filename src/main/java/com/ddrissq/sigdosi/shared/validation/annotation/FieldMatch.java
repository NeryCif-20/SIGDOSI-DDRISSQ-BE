package com.ddrissq.sigdosi.shared.validation.annotation;

import com.ddrissq.sigdosi.shared.validation.validator.FieldMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FieldMatchValidator.class)
public @interface FieldMatch {

    String message() default "Fields do not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();

    String fieldMatch();

}

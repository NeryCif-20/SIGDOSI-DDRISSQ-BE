package com.ddrissq.sigdosi.common.validation.annotation;

import com.ddrissq.sigdosi.common.validation.validator.FieldMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FieldMatchValidator.class)
@Repeatable(value = RangeOrder.List.class)
public @interface RangeOrder {

    String message() default "min value cannot be greater than the max value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String minField();

    String maxField();

    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        RangeOrder[] value();
    }

}

package com.ddrissq.sigdosi.common.validation.compare.annotation;

import com.ddrissq.sigdosi.common.validation.compare.validator.CompareValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CompareValidator.class)
@Repeatable(value = Compare.List.class)
public @interface Compare {

    String message() default "min value cannot be greater than the max value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String firstField();

    String secondField();

    ComparisonOperator operator() default ComparisonOperator.EQUAL;

    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Compare[] value();
    }

}

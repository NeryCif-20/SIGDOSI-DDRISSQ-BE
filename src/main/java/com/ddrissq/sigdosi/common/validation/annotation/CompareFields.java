package com.ddrissq.sigdosi.common.validation.annotation;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.common.validation.validator.CompareFieldsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CompareFieldsValidator.class)
@Repeatable(value = CompareFields.List.class)
public @interface CompareFields {

    String message() default ValidationError.EQUAL;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String first();

    String second();

    ComparisonOperator operator() default ComparisonOperator.EQUAL;

    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CompareFields[] value();
    }

}

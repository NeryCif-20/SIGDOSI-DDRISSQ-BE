package com.ddrissq.sigdosi.common.validation.comparison.annotation;

import com.ddrissq.sigdosi.common.validation.comparison.constant.ComparisonErrorMessageKeys;
import com.ddrissq.sigdosi.common.validation.comparison.validator.CompareValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CompareValidator.class)
@Repeatable(value = Compare.List.class)
public @interface Compare {

    String message() default ComparisonErrorMessageKeys.EQUAL;

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

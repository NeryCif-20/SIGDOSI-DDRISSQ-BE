package com.ddrissq.sigdosi.common.validation.annotation;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.common.validation.validator.NullableNotBlankValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NullableNotBlankValidator.class)
public @interface NullableNotBlank {

    String message() default ValidationError.NULLABLE_NOT_BLANK;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

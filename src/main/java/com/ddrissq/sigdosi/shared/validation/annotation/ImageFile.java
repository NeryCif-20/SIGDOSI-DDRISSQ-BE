package com.ddrissq.sigdosi.shared.validation.annotation;

import com.ddrissq.sigdosi.shared.validation.validator.ImageFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageFileValidator.class)
public @interface ImageFile {

    String message() default "file must be a valid image";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

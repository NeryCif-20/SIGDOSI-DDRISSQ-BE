package com.ddrissq.sigdosi.common.validation.annotation;

import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.common.validation.validator.AllowedGeometryTypesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.locationtech.jts.geom.Geometry;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT, ElementType.TYPE_USE })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AllowedGeometryTypesValidator.class)
public @interface AllowedGeometryTypes {

    String message() default ValidationError.ALLOWED_GEOMETRY_TYPES;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Geometry>[] value();
}

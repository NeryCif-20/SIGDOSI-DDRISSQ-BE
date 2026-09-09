package com.ddrissq.sigdosi.common.validation.geometry.annotation;

import com.ddrissq.sigdosi.common.validation.geometry.constant.GeometryErrorMessageKeys;
import com.ddrissq.sigdosi.common.validation.geometry.validator.GeometryTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.locationtech.jts.geom.Geometry;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GeometryTypeValidator.class)
public @interface GeometryType {

    String message() default GeometryErrorMessageKeys.GEOMETRY_TYPE_INVALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Geometry> expectedType();

}

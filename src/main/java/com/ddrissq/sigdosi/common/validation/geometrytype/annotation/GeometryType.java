package com.ddrissq.sigdosi.common.validation.geometrytype.annotation;

import com.ddrissq.sigdosi.common.validation.geometrytype.validator.GeometryTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.locationtech.jts.geom.Geometry;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GeometryTypeValidator.class)
public @interface GeometryType {

    String message() default "geometry type provided is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Geometry> expectedType();

}

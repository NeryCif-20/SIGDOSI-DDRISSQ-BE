package com.ddrissq.sigdosi.common.validation.geometrytype.validator;

import com.ddrissq.sigdosi.common.validation.geometrytype.annotation.GeometryType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.locationtech.jts.geom.Geometry;

public class GeometryTypeValidator implements ConstraintValidator<GeometryType, Geometry> {

    private Class<? extends Geometry> expectedType;

    @Override
    public void initialize(GeometryType constraintAnnotation) {
        this.expectedType = constraintAnnotation.expectedType();
    }

    @Override
    public boolean isValid(Geometry value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return expectedType.isInstance(value);
    }

}

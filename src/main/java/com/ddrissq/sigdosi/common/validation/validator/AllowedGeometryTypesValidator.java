package com.ddrissq.sigdosi.common.validation.validator;

import com.ddrissq.sigdosi.common.validation.annotation.AllowedGeometryTypes;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.locationtech.jts.geom.Geometry;

import java.util.Arrays;

public class AllowedGeometryTypesValidator implements ConstraintValidator<AllowedGeometryTypes, Geometry> {

    private Class<? extends Geometry>[] value;

    @Override
    public void initialize(AllowedGeometryTypes annotation) {
        this.value = annotation.value();
    }


    @Override
    public boolean isValid(Geometry geometry, ConstraintValidatorContext context) {
        if (geometry == null) {
            return true;
        }
        boolean isValid = Arrays.stream(value).anyMatch(
                type -> type.isInstance(geometry));
        if (!isValid) {
            String[] value = Arrays.stream(this.value)
                            .map(Class::getSimpleName)
                            .toArray(String[]::new);
            context.unwrap(HibernateConstraintValidatorContext.class)
                    .addMessageParameter("value", value);
        }
        return isValid;
    }

}

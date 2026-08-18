package com.ddrissq.sigdosi.shared.validation.validator;

import com.ddrissq.sigdosi.shared.validation.annotation.FieldMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldMatch annotation) {
        this.field = annotation.field();
        this.fieldMatch = annotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        BeanWrapper wrapper = new BeanWrapperImpl(value);
        Object firstValue = wrapper.getPropertyValue(this.field);
        Object secondValue = wrapper.getPropertyValue(this.fieldMatch);
        boolean isValid = Objects.equals(firstValue, secondValue);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(this.fieldMatch)
                    .addConstraintViolation();
        }
        return isValid;
    }

}

package com.ddrissq.sigdosi.common.validation.validator;

import com.ddrissq.sigdosi.common.validation.annotation.RangeOrder;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class RangeOrderValidator implements ConstraintValidator<RangeOrder, Object> {

    private static final Set<Class<?>> ALLOWED_NUMBER_TYPES = Set.of(
            Integer.class,
            Byte.class,
            Short.class,
            Long.class,
            BigDecimal.class,
            BigInteger.class);

    private String minField;
    private String maxField;

    @Override
    public void initialize(RangeOrder annotation) {
        this.minField = annotation.minField();
        this.maxField = annotation.maxField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        BeanWrapper wrapper = new BeanWrapperImpl(value);
        Object minValue = wrapper.getPropertyValue(this.minField);
        Object maxValue = wrapper.getPropertyValue(this.maxField);
        if (minValue == null || maxValue == null) {
            return true;
        }
        if (!Objects.equals(minValue.getClass(), maxValue.getClass()) || !isSupportedType(minValue)) {
            return false;
        }
        boolean isValid = compareValues(minValue, maxValue);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(this.minField)
                    .addConstraintViolation();
        }
        return isValid;
    }

    private boolean isSupportedType(Object object) {
        boolean isNumberType = ALLOWED_NUMBER_TYPES.contains(
                object.getClass());
        boolean isTemporalType = object instanceof TemporalAccessor
                || object instanceof Date;
        return (isNumberType || isTemporalType)
                && object instanceof Comparable<?>;
    }

    @SuppressWarnings("unchecked")
    private boolean compareValues(Object min, Object max) {
        Comparable<Object> minComparable = (Comparable<Object>) min;
        return minComparable.compareTo(max) <= 0;
    }

}

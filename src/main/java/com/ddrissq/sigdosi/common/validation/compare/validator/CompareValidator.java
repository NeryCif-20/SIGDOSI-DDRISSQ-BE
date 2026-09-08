package com.ddrissq.sigdosi.common.validation.compare.validator;

import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.common.validation.compare.annotation.Compare;
import com.ddrissq.sigdosi.common.validation.compare.annotation.ComparisonOperator;
import com.ddrissq.sigdosi.common.validation.compare.constant.CompareErrorMessageKeys;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.UnexpectedTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.math.BigDecimal;
import java.math.BigInteger;

@RequiredArgsConstructor
public class CompareValidator implements ConstraintValidator<Compare, Object> {

    private final MessageService messageService;

    private String firstField;
    private String secondField;
    private ComparisonOperator operator;

    @Override
    public void initialize(Compare annotation) {
        this.firstField = annotation.firstField();
        this.secondField = annotation.secondField();
        this.operator = annotation.operator();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        BeanWrapper wrapper = new BeanWrapperImpl(value);
        Object firstFieldValue = wrapper.getPropertyValue(this.firstField);
        Object secondFieldValue = wrapper.getPropertyValue(this.secondField);
        if (firstFieldValue == null || secondFieldValue == null) {
            return true;
        }
        boolean isValid = switch (operator) {
            case EQUAL -> areEqual(firstFieldValue, secondFieldValue);
            case GREATER_THAN -> compare(firstFieldValue, secondFieldValue) > 0;
            case GREATER_THAN_OR_EQUAL -> compare(firstFieldValue, secondFieldValue) >= 0;
            case LESS_THAN -> compare(firstFieldValue, secondFieldValue) < 0;
            case LESS_THAN_OR_EQUAL -> compare(firstFieldValue, secondFieldValue) <= 0;
        };
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(firstField)
                    .addConstraintViolation();
        }
        return isValid;
    }

    private boolean areEqual(Object firstValue, Object secondValue) {
        if (areCharSequences(firstValue, secondValue)) {
            return firstValue.toString().contentEquals(secondValue.toString());
        }
        if (areNumerics(firstValue, secondValue)) {
            return compareNumbers(firstValue, secondValue) == 0;
        }
        throw new UnexpectedTypeException(
                messageService.getMessage(
                        CompareErrorMessageKeys.COMPARISON_EQUALITY_TYPES_UNSUPPORTED,
                        firstValue.getClass(),
                        secondValue.getClass()));
    }

    private int compare(Object firstValue, Object secondValue) {
        if (areNumerics(firstValue, secondValue)) {
            return compareNumbers(firstValue, secondValue);
        }
        throw new UnexpectedTypeException(
                messageService.getMessage(
                        CompareErrorMessageKeys.COMPARISON_ORDINAL_TYPES_UNSUPPORTED,
                        firstValue.getClass(),
                        secondValue.getClass()));
    }

    private boolean areCharSequences(Object first, Object second) {
        return first instanceof CharSequence && second instanceof CharSequence;
    }

    private boolean areNumerics(Object first, Object second) {
        return first instanceof Number && second instanceof Number;
    }

    private int compareNumbers(Object firstValue, Object secondValue) {
        BigDecimal firstNumber = toBigDecimal((Number) firstValue);
        BigDecimal secondNumber = toBigDecimal((Number) secondValue);
        return firstNumber.compareTo(secondNumber);
    }

    private BigDecimal toBigDecimal(Number number) {
        return switch (number) {
            case Byte value -> BigDecimal.valueOf(value.longValue());
            case Short value -> BigDecimal.valueOf(value.longValue());
            case Integer value -> BigDecimal.valueOf(value.longValue());
            case Long value -> BigDecimal.valueOf(value);
            case BigInteger value -> new BigDecimal(value);
            case BigDecimal value -> value;
            default -> throw new UnexpectedTypeException(
                    messageService.getMessage(
                            CompareErrorMessageKeys.COMPARISON_NUMERIC_TYPE_UNSUPPORTED,
                            number.getClass().getCanonicalName()));
        };
    }

}

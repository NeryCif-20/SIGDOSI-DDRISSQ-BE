package com.ddrissq.sigdosi.common.validation.validator;

import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.common.validation.annotation.CompareFields;
import com.ddrissq.sigdosi.common.validation.annotation.ComparisonOperator;
import com.ddrissq.sigdosi.common.validation.error.ValidationError;
import com.ddrissq.sigdosi.common.validation.error.ValidationErrorDescriptor;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.UnexpectedTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CompareFieldsValidator implements ConstraintValidator<CompareFields, Object> {

    private final MessageService messageService;

    private String first;
    private String second;
    private ComparisonOperator operator;

    @Override
    public void initialize(CompareFields annotation) {
        this.first = annotation.first();
        this.second = annotation.second();
        this.operator = annotation.operator();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        BeanWrapper wrapper = new BeanWrapperImpl(value);
        Object firstValue = wrapper.getPropertyValue(this.first);
        Object secondValue = wrapper.getPropertyValue(this.second);
        if (firstValue == null || secondValue == null) {
            return true;
        }
        boolean isValid = compareValues(firstValue, secondValue);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            String defaultMessage = context.getDefaultConstraintMessageTemplate();
            String messageTemplate = ValidationError.EQUAL.equals(defaultMessage)
                    ? getMessageKey()
                    : defaultMessage;
            context.buildConstraintViolationWithTemplate(
                    messageTemplate)
                    .addPropertyNode(first)
                    .addConstraintViolation();
        }
        return isValid;
    }

    private boolean compareValues(Object firstValue, Object secondValue) {
        return switch (this.operator) {
            case EQUAL -> areEqual(firstValue, secondValue);
            case NOT_EQUAL -> !areEqual(firstValue, secondValue);
            case GREATER_THAN -> compareOrder(firstValue, secondValue) > 0;
            case GREATER_THAN_OR_EQUAL -> compareOrder(firstValue, secondValue) >= 0;
            case LESS_THAN -> compareOrder(firstValue, secondValue) < 0;
            case LESS_THAN_OR_EQUAL -> compareOrder(firstValue, secondValue) <= 0;
        };
    }

    private String getMessageKey() {
        return switch (this.operator) {
            case EQUAL -> ValidationError.EQUAL;
            case NOT_EQUAL -> ValidationError.NOT_EQUAL;
            case GREATER_THAN -> ValidationError.GREATER_THAN;
            case GREATER_THAN_OR_EQUAL -> ValidationError.GREATER_THAN_OR_EQUAL;
            case LESS_THAN -> ValidationError.LESS_THAN;
            case LESS_THAN_OR_EQUAL -> ValidationError.LESS_THAN_OR_EQUAL;
        };
    }

    private boolean areEqual(Object firstValue, Object secondValue) {
        if (areCharSequences(firstValue, secondValue)) {
            return firstValue.toString().contentEquals(secondValue.toString());
        }
        return compareOrder(firstValue, secondValue) == 0;
    }

    private int compareOrder(Object firstValue, Object secondValue) {
        if (areNumbers(firstValue, secondValue)) {
            return compareNumbers(firstValue, secondValue);
        }
        return throwUnsupportedType(firstValue, secondValue);
    }

    private boolean areCharSequences(Object first, Object second) {
        return first instanceof CharSequence && second instanceof CharSequence;
    }

    private boolean areNumbers(Object first, Object second) {
        return first instanceof Number && second instanceof Number;
    }

    private int compareNumbers(Object firstValue, Object secondValue) {
        BigDecimal firstNumber = toBigDecimal(firstValue);
        BigDecimal secondNumber = toBigDecimal(secondValue);
        return firstNumber.compareTo(secondNumber);
    }

    private BigDecimal toBigDecimal(Object value) {
        return switch (value) {
            case Byte number -> BigDecimal.valueOf(number.longValue());
            case Short number -> BigDecimal.valueOf(number.longValue());
            case Integer number -> BigDecimal.valueOf(number.longValue());
            case Long number -> BigDecimal.valueOf(number);
            case BigInteger number -> new BigDecimal(number);
            case BigDecimal number -> number;
            case null, default -> throwUnsupportedType(value);
        };
    }

    private <T> T throwUnsupportedType(Object... values) {
        String types = Arrays.stream(values)
                .map(value -> value.getClass().getSimpleName())
                .collect(Collectors.joining(", ", "{", "}"));
        throw new UnexpectedTypeException(
                messageService.getMessage(
                        ValidationErrorDescriptor.UNSUPPORTED_TYPES.messageKey(),
                        types));
    }

}

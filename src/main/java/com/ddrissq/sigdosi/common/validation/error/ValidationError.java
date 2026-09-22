package com.ddrissq.sigdosi.common.validation.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationError {

    public static final String REQUIRED = "{error.validation.required}";
    public static final String SIZE = "{error.validation.size}";
    public static final String EMAIL = "{error.validation.email}";
    public static final String POSITIVE = "{error.validation.positive}";
    public static final String POSITIVE_OR_ZERO = "{error.validation.positive-or-zero}";
    public static final String EQUAL = "{error.validation.equal}";
    public static final String NOT_EQUAL = "{error.validation.not-equal}";
    public static final String GREATER_THAN = "{error.validation.greater-than}";
    public static final String GREATER_THAN_OR_EQUAL = "{error.validation.greater-than-or-equal}";
    public static final String LESS_THAN = "{error.validation.less-than}";
    public static final String LESS_THAN_OR_EQUAL = "{error.validation.less-than-or-equal}";
    public static final String NULLABLE_NOT_BLANK = "{error.validation.nullable-not-blank}";

}

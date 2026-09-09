package com.ddrissq.sigdosi.common.validation.comparison.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ComparisonErrorMessageKeys {

    public static final String EQUALITY_TYPES_UNSUPPORTED = "compare.equality-types.unsupported";
    public static final String ORDINAL_TYPES_UNSUPPORTED = "compare.ordinal-types.unsupported";
    public static final String NUMERIC_TYPE_UNSUPPORTED = "compare.numeric-type.unsupported";
    public static final String EQUAL = "compare.equal.invalid";
    public static final String NOT_EQUAL = "compare.not-equal.invalid";
    public static final String GREATER_THAN = "compare.greater-than.invalid";
    public static final String GREATER_OR_EQUAL = "compare.greater-than-or-equal.invalid";
    public static final String LESS_THAN = "compare.less-than.invalid";
    public static final String LESS_OR_EQUAL = "compare.less-than-or-equal.invalid";

}

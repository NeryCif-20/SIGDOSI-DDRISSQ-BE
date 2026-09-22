package com.ddrissq.sigdosi.common.mapping.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.Named;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringFunctions {

    @Named(value = "toUpperCase")
    public static String toUpperCase(String value) {
        return value == null
                ? null :
                value.trim().toUpperCase();
    }

    @Named(value = "toLowerCase")
    public static String toLowerCase(String value) {
        return value == null
                ? null
                : value.trim().toLowerCase();
    }

    @Named(value = "capitalize")
    public static String capitalize(String value) {
        return value == null
                ? null
                : StringUtils.capitalize(value.trim().toLowerCase());
    }

}

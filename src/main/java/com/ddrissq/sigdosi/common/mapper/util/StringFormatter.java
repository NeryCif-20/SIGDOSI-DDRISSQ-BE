package com.ddrissq.sigdosi.common.mapper.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.Named;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringFormatter {

    @Named(value = "toUpperCase")
    public static String toUpperCase(String value) {
        return value == null ? null : value.trim().toUpperCase();
    }

    @Named(value = "capitalize")
    public static String capitalize(String value) {
        return value == null ? null : StringUtils.capitalize(value.trim().toLowerCase());
    }

}

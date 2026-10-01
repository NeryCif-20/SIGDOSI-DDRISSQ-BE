package com.ddrissq.sigdosi.common.mapping.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.Named;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringFunctions {

    @Named(value = "toUpperCase")
    public static String toUpperCase(String value) {
        return !StringUtils.hasText(value)
                ? value
                : value.trim().toUpperCase();
    }

    @Named(value = "toLowerCase")
    public static String toLowerCase(String value) {
        return !StringUtils.hasText(value)
                ? value
                : value.trim().toLowerCase();
    }

    @Named(value = "capitalize")
    public static String capitalize(String value) {
        if (!StringUtils.hasText(value)) {
            return value;
        }
        return Arrays.stream(value.trim().toLowerCase().split("\\s+"))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining(" "));
    }

}

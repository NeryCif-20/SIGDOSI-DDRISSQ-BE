package com.ddrissq.sigdosi.common.mapper.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.Named;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringMapper {

    @Named(value = "toUpperCase")
    public static String toUpperCase(String value) {
        return value == null ? null : value.toUpperCase();
    }

}

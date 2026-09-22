package com.ddrissq.sigdosi.common.file.validation.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileValidationError {

    public static final String ALLOWED_MIME_TYPES = "{error.file.validation.allowed-mime-types}";
    public static final String MAX_SIZE = "{error.file.validation.max-size}";
    public static final String NOT_EMPTY = "{error.file.validation.not-empty}";

}

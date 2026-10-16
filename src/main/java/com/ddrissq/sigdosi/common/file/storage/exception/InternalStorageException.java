package com.ddrissq.sigdosi.common.file.storage.exception;

import lombok.Getter;

@Getter
public class InternalStorageException extends RuntimeException {

    public InternalStorageException(String message) {
        super(message);
    }

    public InternalStorageException(String message, Throwable cause) {
        super(message, cause);
    }

}

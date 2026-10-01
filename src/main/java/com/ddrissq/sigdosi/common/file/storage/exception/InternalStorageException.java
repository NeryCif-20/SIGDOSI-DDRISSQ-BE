package com.ddrissq.sigdosi.common.file.storage.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class InternalStorageException extends RuntimeException {

    private final List<String> undeletedFiles;

    public InternalStorageException(String message) {
        super(message);
        this.undeletedFiles = List.of();
    }

    public InternalStorageException(String message, List<String> undeletedFiles) {
        super(message);
        this.undeletedFiles = List.copyOf(undeletedFiles);
    }

}

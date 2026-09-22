package com.ddrissq.sigdosi.common.file.storage.exception;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.exception.BaseException;

public class StorageException extends BaseException {

    public StorageException(ErrorDescriptor descriptor) {
        super(descriptor);
    }

}

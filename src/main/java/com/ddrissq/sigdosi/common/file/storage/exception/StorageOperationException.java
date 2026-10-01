package com.ddrissq.sigdosi.common.file.storage.exception;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.exception.BaseException;

public class StorageOperationException extends BaseException {

    public StorageOperationException(ErrorDescriptor descriptor) {
        super(descriptor);
    }

}

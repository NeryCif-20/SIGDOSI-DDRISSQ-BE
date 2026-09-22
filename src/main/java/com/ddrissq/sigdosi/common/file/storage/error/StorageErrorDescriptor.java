package com.ddrissq.sigdosi.common.file.storage.error;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.error.ErrorTitle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StorageErrorDescriptor implements ErrorDescriptor {

    INITIALIZATION_FAILED("error.file.storage.initialization.failed", ErrorTitle.FILE_STORAGE),
    SAVE_FAILED("error.file.storage.save.failed", ErrorTitle.FILE_STORAGE),
    DELETE_FAILED("error.file.storage.delete.failed", ErrorTitle.FILE_STORAGE),
    LOAD_FAILED("error.file.storage.load.failed", ErrorTitle.FILE_STORAGE),
    NOT_FOUND("error.file.storage.not-found", ErrorTitle.RESOURCE_NOT_FOUND);

    private final String messageKey;
    private final ErrorTitle errorTitle;

    @Override
    public String code() {
        return this.name();
    }

    @Override
    public String messageKey() {
        return this.messageKey;
    }

    @Override
    public String titleKey() {
        return this.errorTitle.key();
    }

}

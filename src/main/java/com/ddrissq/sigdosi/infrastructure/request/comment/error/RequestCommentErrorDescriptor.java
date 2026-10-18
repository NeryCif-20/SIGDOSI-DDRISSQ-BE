package com.ddrissq.sigdosi.infrastructure.request.comment.error;

import com.ddrissq.sigdosi.common.error.ErrorDescriptor;
import com.ddrissq.sigdosi.common.error.ErrorTitle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RequestCommentErrorDescriptor implements ErrorDescriptor {

    NOT_FOUND("error.request-comment.not-found", ErrorTitle.RESOURCE_NOT_FOUND),
    UPDATE_FORBIDDEN("error.request-comment.update.forbidden", ErrorTitle.BUSINESS_RULE),
    DELETE_FORBIDDEN("error.request-comment.delete.forbidden", ErrorTitle.BUSINESS_RULE);

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

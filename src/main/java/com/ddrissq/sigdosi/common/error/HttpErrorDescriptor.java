package com.ddrissq.sigdosi.common.error;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HttpErrorDescriptor implements ErrorDescriptor {

    REQUEST_RESOURCE_NOT_FOUND(
            "error.http.request.resource.not-found",
            ErrorTitle.HTTP_REQUEST_RESOURCE),
    REQUEST_VALIDATION_FAILED(
            "error.http.request.validation.failed",
            ErrorTitle.HTTP_REQUEST_VALIDATION),
    REQUEST_BODY_REQUIRED(
            "error.http.request.body.required",
            ErrorTitle.HTTP_REQUEST_BODY),
    REQUEST_BODY_MALFORMED(
            "error.http.request.body.malformed",
            ErrorTitle.HTTP_REQUEST_BODY),
    REQUEST_BODY_TYPE_MISMATCHED(
            "error.http.request.body.type.mismatched",
            ErrorTitle.HTTP_REQUEST_BODY),
    REQUEST_BODY_STRUCTURE_MISMATCHED(
            "error.http.request.body.structure.mismatched",
            ErrorTitle.HTTP_REQUEST_BODY),
    REQUEST_PATH_VARIABLE_REQUIRED(
            "error.http.request.path-variable.required",
            ErrorTitle.HTTP_REQUEST_PATH_VARIABLE),
    REQUEST_COOKIE_REQUIRED(
            "error.http.request.cookie.required",
            ErrorTitle.HTTP_REQUEST_COOKIE),
    REQUEST_METHOD_NOT_ALLOWED(
            "error.http.request.method.not-allowed",
            ErrorTitle.HTTP_REQUEST_METHOD),
    REQUEST_METHOD_ARGUMENT_TYPE_MISMATCHED(
            "error.http.request.method.argument.type.mismatched",
            ErrorTitle.HTTP_REQUEST_METHOD),
    REQUEST_MEDIA_TYPE_UNSUPPORTED(
            "error.http.request.media-type.unsupported",
            ErrorTitle.HTTP_REQUEST_MEDIA_TYPE),
    REQUEST_MEDIA_TYPE_UNACCEPTED(
            "error.http.request.media-type.unaccepted",
            ErrorTitle.HTTP_REQUEST_MEDIA_TYPE);

    private final String messageKey;
    private final ErrorTitle errorTitle;

    @Override
    public String code() {
        return name();
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

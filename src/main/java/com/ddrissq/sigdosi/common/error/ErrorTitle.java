package com.ddrissq.sigdosi.common.error;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorTitle {

    HTTP_REQUEST_RESOURCE("error.title.http.request.resource"),
    HTTP_REQUEST_VALIDATION("error.title.http.request.validation"),
    HTTP_REQUEST_BODY("error.title.http.request.body"),
    HTTP_REQUEST_PATH_VARIABLE("error.title.http.request.path-variable"),
    HTTP_REQUEST_COOKIE("error.title.http.request.cookie"),
    HTTP_REQUEST_METHOD("error.title.http.request.method"),
    HTTP_REQUEST_MEDIA_TYPE("error.title.http.request.media-type"),
    RESOURCE_NOT_FOUND("error.title.resource.not-found"),
    RESOURCE_ALREADY_EXISTS("error.title.resource.already-exists"),
    BUSINESS_RULE("error.title.business-rule"),
    FILE_STORAGE("error.title.file.storage"),
    MAIL("error.title.mail"),
    INTERNAL_SERVER("error.title.internal-server"),
    AUTHENTICATION("error.title.authentication"),
    AUTHORIZATION("error.title.authorization");

    private final String key;

    public String key() {
        return this.key;
    }

}

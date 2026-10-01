package com.ddrissq.sigdosi.iam.auth.mail.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthMailTemplate {

    PASSWORD_SETUP("/mail/auth/password-setup");

    private final String path;

}

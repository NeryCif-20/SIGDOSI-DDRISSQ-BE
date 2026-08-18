package com.ddrissq.sigdosi.shared.mail.service;

import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenPurpose;

public interface MailService {

    void sendPasswordEmail(String email, String name, String token, PasswordTokenPurpose purpose);

}

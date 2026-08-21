package com.ddrissq.sigdosi.common.mail.service;

import com.ddrissq.sigdosi.common.mail.model.EmailData;

public interface MailService {

    void sendEmail(EmailData data);

}

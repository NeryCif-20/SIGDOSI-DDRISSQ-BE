package com.ddrissq.sigdosi.common.mail.service;

import com.ddrissq.sigdosi.common.mail.model.SendMailCommand;

public interface MailService {

    void sendMail(SendMailCommand data);

}

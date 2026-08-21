package com.ddrissq.sigdosi.iam.auth.mail.service;

import com.ddrissq.sigdosi.common.mail.model.EmailData;
import com.ddrissq.sigdosi.common.mail.service.MailService;
import com.ddrissq.sigdosi.configuration.ApplicationProperties;
import com.ddrissq.sigdosi.iam.auth.mail.constant.PasswordSetEmailConstants;
import com.ddrissq.sigdosi.iam.auth.mail.model.PasswordSetEmailData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@RequiredArgsConstructor
@Service
public class AuthMailService {

    private final MailService service;
    private final ApplicationProperties applicationProps;

    public void sendSetupPasswordEmail(PasswordSetEmailData data) {
        String template = PasswordSetEmailConstants.SETUP_TEMPLATE;
        String path = PasswordSetEmailConstants.SETUP_PATH;
        sendSetPasswordEmail(data, template, path);
    }

    public void sendResetPasswordEmail(PasswordSetEmailData data) {
        String template = PasswordSetEmailConstants.RESET_TEMPLATE;
        String path = PasswordSetEmailConstants.RESET_PATH;
        sendSetPasswordEmail(data, template, path);
    }

    private void sendSetPasswordEmail(PasswordSetEmailData data, String template, String path) {
        String uri = applicationProps.client().origin();
        ZoneId zone = applicationProps.zone();
        String buttonLink = data.buildButtonLink(uri, path);
        String expiresAt = data.formatExpiresAt(zone);
        EmailData emailData = EmailData.builder()
                .to(data.to())
                .subject(PasswordSetEmailConstants.SUBJECT)
                .template(template)
                .templateVariable("name", data.name())
                .templateVariable("buttonLink", buttonLink)
                .templateVariable("expiresAt", expiresAt)
                .build();
        service.sendEmail(emailData);
    }


}

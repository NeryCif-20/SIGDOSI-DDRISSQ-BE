package com.ddrissq.sigdosi.iam.auth.mail.service;

import com.ddrissq.sigdosi.common.mail.model.EmailData;
import com.ddrissq.sigdosi.common.mail.service.MailService;
import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.configuration.ApplicationProperties;
import com.ddrissq.sigdosi.iam.auth.mail.configuration.AuthMailProperties;
import com.ddrissq.sigdosi.iam.auth.mail.model.PasswordSetEmailData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@RequiredArgsConstructor
@Service
public class AuthMailService {

    private final MailService service;
    private final ApplicationProperties applicationProps;
    private final AuthMailProperties authMailProps;
    private final MessageService messageService;

    public void sendSetupPasswordEmail(PasswordSetEmailData data) {
        String path = authMailProps.paths().setupPassword();
        String action = Action.SETUP.name().toLowerCase();
        sendSetPasswordEmail(data, action, path);
    }

    public void sendResetPasswordEmail(PasswordSetEmailData data) {
        String path = authMailProps.paths().resetPassword();
        String action = Action.RESET.name().toLowerCase();
        sendSetPasswordEmail(data, action, path);
    }

    private void sendSetPasswordEmail(PasswordSetEmailData data, String action, String path) {
        String subject = messageService.getMessage(
                "auth.mail." + action + "-password.subject");
        String uri = applicationProps.client().origin();
        ZoneId zone = applicationProps.zone();
        String buttonLink = data.buildButtonLink(uri, path);
        String expiresAt = data.formatExpiresAt(zone);
        EmailData emailData = EmailData.builder()
                .to(data.to())
                .subject(subject)
                .template(data.template())
                .templateVariable("action", action)
                .templateVariable("name", data.name())
                .templateVariable("buttonLink", buttonLink)
                .templateVariable("expiresAt", expiresAt)
                .build();
        service.sendEmail(emailData);
    }

    private enum Action {

        SETUP,
        RESET

    }

}

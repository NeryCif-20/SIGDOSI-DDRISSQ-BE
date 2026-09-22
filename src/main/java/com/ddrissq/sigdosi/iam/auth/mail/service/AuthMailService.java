package com.ddrissq.sigdosi.iam.auth.mail.service;

import com.ddrissq.sigdosi.common.mail.model.MailData;
import com.ddrissq.sigdosi.common.mail.service.MailService;
import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.configuration.ApplicationProperties;
import com.ddrissq.sigdosi.iam.auth.mail.configuration.AuthMailProperties;
import com.ddrissq.sigdosi.iam.auth.mail.model.PasswordSetMailData;
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

    public void sendSetPasswordMail(PasswordSetMailData data) {
        String action = data.action().name().toLowerCase();
        String subject = messageService.getMessage(
                "template.mail.auth.set-password." + action + ".subject");
        String uri = applicationProps.client().origin();
        ZoneId zone = applicationProps.zone();
        String path = authMailProps.setPasswordPaths().get(data.action());
        String buttonLink = data.buildButtonLink(uri, path);
        String expiresAt = data.formatExpiresAt(zone);
        MailData mailData = MailData.builder()
                .to(data.to())
                .subject(subject)
                .template(data.template())
                .templateVariable("action", action)
                .templateVariable("name", data.name())
                .templateVariable("buttonLink", buttonLink)
                .templateVariable("expiresAt", expiresAt)
                .build();
        service.sendMail(mailData);
    }

}

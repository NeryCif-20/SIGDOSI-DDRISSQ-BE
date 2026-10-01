package com.ddrissq.sigdosi.iam.auth.mail.service;

import com.ddrissq.sigdosi.common.mail.model.SendMailCommand;
import com.ddrissq.sigdosi.common.mail.service.MailService;
import com.ddrissq.sigdosi.common.message.service.MessageService;
import com.ddrissq.sigdosi.configuration.ApplicationProperties;
import com.ddrissq.sigdosi.iam.auth.mail.model.PasswordSetupMailData;
import com.ddrissq.sigdosi.iam.auth.mail.configuration.AuthMailProperties;
import com.ddrissq.sigdosi.iam.auth.mail.model.AuthMailTemplate;
import com.ddrissq.sigdosi.iam.auth.model.PasswordSetupAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class AuthMailService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");

    private final MailService service;
    private final ApplicationProperties applicationProps;
    private final AuthMailProperties authMailProps;
    private final MessageService messageService;

    public void sendPasswordSetupMail(PasswordSetupMailData data) {
        String action = data.action().name().toLowerCase();
        String subject = messageService.getMessage(
                "template.mail.auth.password-setup.%s.subject".formatted(action));
        String buttonLink = buildPasswordSetupButtonLink(data.action(), data.token());
        String expiresAt = formatPasswordSetupExpiration(data.expiresAt());
        SendMailCommand sendMailCommand = SendMailCommand.builder()
                .to(data.to())
                .subject(subject)
                .template(AuthMailTemplate.PASSWORD_SETUP.getPath())
                .templateVariable("action", action)
                .templateVariable("name", data.name())
                .templateVariable("buttonLink", buttonLink)
                .templateVariable("expiresAt", expiresAt)
                .build();
        service.sendMail(sendMailCommand);
    }

    private String buildPasswordSetupButtonLink(PasswordSetupAction action, String token) {
        return UriComponentsBuilder
                .fromUriString(applicationProps.client().origin())
                .path(authMailProps.setPasswordPaths().get(action)
                        .formatted(token))
                .build()
                .toUriString();
    }

    public String formatPasswordSetupExpiration(Instant expiresAt) {
        return expiresAt.atZone(applicationProps.zone()).format(FORMATTER);
    }

}

package com.ddrissq.sigdosi.shared.mail.service;

import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenPurpose;
import com.ddrissq.sigdosi.iam.security.configuration.SecurityProperties;
import com.ddrissq.sigdosi.shared.configuration.application.ApplicationProperties;
import com.ddrissq.sigdosi.shared.mail.exception.MailExceptionMessages;
import com.ddrissq.sigdosi.shared.mail.exception.MailSendingException;
import com.ddrissq.sigdosi.shared.mail.model.MailCreateParams;
import com.ddrissq.sigdosi.shared.mail.model.PasswordMailContent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@RequiredArgsConstructor
@Async(value = "mailTaskExecutor")
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final ApplicationProperties applicationProperties;
    private final SecurityProperties securityProperties;

    @Override
    public void sendPasswordEmail(String email, String name, String token, PasswordTokenPurpose purpose) {
        PasswordMailContent content = PasswordMailContent.from(purpose);
        String link = buildPasswordLink(content.path(), token);
        String exp = buildPasswordLinkExpires();
        MailCreateParams params = MailCreateParams.builder()
                .to(email)
                .subject(content.subject())
                .template("/mail/auth/password-action")
                .templateVariable("name", name)
                .templateVariable("link", link)
                .templateVariable("exp", exp)
                .templateVariable("title", content.title())
                .templateVariable("actionText", content.actionText())
                .templateVariable("buttonText", content.buttonText())
                .build();
        this.sendEmail(params);
    }

    private void sendEmail(MailCreateParams params) {
        try {
            Context context = new Context();
            context.setVariables(params.templateVariables());
            String html = templateEngine.process(params.template(), context);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, true, StandardCharsets.UTF_8.name());
            helper.setTo(params.to());
            helper.setSubject(params.subject());
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException ex) {
            throw new MailSendingException(
                    MailExceptionMessages.EMAIL_SENDING_FAILED);
        }
    }


    private String buildPasswordLink(String path, String token) {
        String uri = applicationProperties.getClient().getOrigin();
        return UriComponentsBuilder
                .fromUriString(uri)
                .path(path)
                .queryParam("token", token)
                .build()
                .toUriString();
    }

    private String buildPasswordLinkExpires() {
        Duration expirationTime = securityProperties.getPasswordToken()
                .getExpirationTime();
        return DurationStyle.SIMPLE.print(expirationTime);
    }

}

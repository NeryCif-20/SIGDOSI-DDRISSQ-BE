package com.ddrissq.sigdosi.common.mail.service;

import com.ddrissq.sigdosi.common.mail.exception.InternalMailException;
import com.ddrissq.sigdosi.common.mail.model.SendMailCommand;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async(value = "mailAsyncExecutor")
    @Retryable(
            retryFor = InternalMailException.class,
            backoff = @Backoff(delay = 30_000, multiplier = 4))
    @Override
    public void sendMail(SendMailCommand command) {
        try {
            Context context = new Context();
            context.setVariables(command.templateVariables());
            String html = templateEngine.process(command.template(), context);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, true, StandardCharsets.UTF_8.name());
            helper.setTo(command.to());
            helper.setSubject(command.subject());
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException ex) {
            log.error("Failed to send the email to '{}' with the subject '{}'.",
                    command.to(),
                    command.subject(),
                    ex);
            throw new InternalMailException(ex.getMessage(), ex);
        }
    }

    @Recover
    public void recoverSendMail(InternalMailException ex, SendMailCommand command) {
        log.error("Failed to send the email to '{}' with the subject '{}' after all retry attempts.",
                command.to(),
                command.subject(),
                ex);
    }

}

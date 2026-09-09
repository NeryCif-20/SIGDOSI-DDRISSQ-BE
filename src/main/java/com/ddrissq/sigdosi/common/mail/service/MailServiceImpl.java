package com.ddrissq.sigdosi.common.mail.service;

import com.ddrissq.sigdosi.common.mail.constant.MailErrorMessageKeys;
import com.ddrissq.sigdosi.common.mail.exception.MailException;
import com.ddrissq.sigdosi.common.mail.model.EmailData;
import com.ddrissq.sigdosi.common.message.service.MessageService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Async(value = "mailTaskExecutor")
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final MessageService messageService;

    @Override
    public void sendEmail(EmailData data) {
        try {
            Context context = new Context();
            context.setVariables(data.templateVariables());
            String html = templateEngine.process(data.template(), context);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, true, StandardCharsets.UTF_8.name());
            helper.setTo(data.to());
            helper.setSubject(data.subject());
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException ex) {
            throw new MailException(
                    messageService.getMessage(
                            MailErrorMessageKeys.SEND_FAILED));
        }
    }

}

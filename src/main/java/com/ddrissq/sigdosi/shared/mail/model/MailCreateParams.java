package com.ddrissq.sigdosi.shared.mail.model;

import lombok.Builder;
import lombok.Singular;

import java.util.Map;

@Builder
public record MailCreateParams(
        String to,
        String subject,
        String template,
        @Singular
        Map<String, Object> templateVariables) {
}

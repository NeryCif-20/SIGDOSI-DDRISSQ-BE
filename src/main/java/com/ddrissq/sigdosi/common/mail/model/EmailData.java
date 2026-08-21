package com.ddrissq.sigdosi.common.mail.model;

import lombok.Builder;
import lombok.Singular;

import java.util.Map;

@Builder
public record EmailData(
        String to,
        String subject,
        String template,
        @Singular
        Map<String, Object> templateVariables) {
}

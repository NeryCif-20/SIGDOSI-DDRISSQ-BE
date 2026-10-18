package com.ddrissq.sigdosi.common.mapping.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.Named;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringFunctions {

    private static final PolicyFactory PLAIN_TEXT = new HtmlPolicyBuilder()
            .toFactory();
    private static final PolicyFactory FORMATTING_BLOCKS_AND_LINKS = Sanitizers.FORMATTING
            .and(Sanitizers.BLOCKS)
            .and(Sanitizers.LINKS);

    @Named(value = "toUpperCase")
    public static String toUpperCase(String value) {
        return !StringUtils.hasText(value)
                ? value
                : value.trim().toUpperCase();
    }

    @Named(value = "toLowerCase")
    public static String toLowerCase(String value) {
        return !StringUtils.hasText(value)
                ? value
                : value.trim().toLowerCase();
    }

    @Named(value = "capitalize")
    public static String capitalize(String value) {
        if (!StringUtils.hasText(value)) {
            return value;
        }
        return Arrays.stream(value.trim().toLowerCase().split("\\s+"))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining(" "));
    }

    @Named(value = "sanitizePlainText")
    public static String sanitizePlainText(String value) {
        return sanitize(value, PLAIN_TEXT);
    }

    @Named(value = "sanitizeFormattingBlocksAndLinks")
    public static String sanitizeFormattingBlocksAndLinks(String value) {
        return sanitize(value, FORMATTING_BLOCKS_AND_LINKS);
    }

    private static String sanitize(String value, PolicyFactory policy) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return policy.sanitize(value);
    }

}

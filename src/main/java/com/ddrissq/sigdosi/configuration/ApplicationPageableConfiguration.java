package com.ddrissq.sigdosi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class ApplicationPageableConfiguration {

    @Bean
    PageableHandlerMethodArgumentResolverCustomizer resolverCustomizer() {
        return resolver -> {
            resolver.setFallbackPageable(Pageable.unpaged());
        };
    }

}

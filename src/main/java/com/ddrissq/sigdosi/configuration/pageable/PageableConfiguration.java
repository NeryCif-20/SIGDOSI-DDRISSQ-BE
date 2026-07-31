package com.ddrissq.sigdosi.configuration.pageable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class PageableConfiguration {

    @Bean
    PageableHandlerMethodArgumentResolverCustomizer resolverCustomizer() {
        return resolver -> {
            resolver.setFallbackPageable(Pageable.unpaged());
        };
    }

}

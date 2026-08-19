package com.ddrissq.sigdosi.common.configuration.auditing;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditingConfiguration {

    @PostConstruct
    void init() {
        System.out.println(">>> AUDITING CONFIGURATION CARGADA <<<");
    }

}

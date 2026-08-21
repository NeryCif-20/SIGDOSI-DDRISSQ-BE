package com.ddrissq.sigdosi.shared.annotation;

import com.ddrissq.sigdosi.TestcontainersConfiguration;
import com.ddrissq.sigdosi.configuration.ApplicationAuditingConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@DataJpaTest
@Import(value = {TestcontainersConfiguration.class, ApplicationAuditingConfiguration.class})
public @interface JpaTest {
}

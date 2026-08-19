package com.ddrissq.sigdosi.shared.annotation;

import com.ddrissq.sigdosi.TestcontainersConfiguration;
import com.ddrissq.sigdosi.common.configuration.auditing.AuditingConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@DataJpaTest
@Import(value = {TestcontainersConfiguration.class, AuditingConfiguration.class})
public @interface JpaTest {
}

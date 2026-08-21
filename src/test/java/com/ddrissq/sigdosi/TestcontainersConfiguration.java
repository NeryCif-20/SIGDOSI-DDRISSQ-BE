package com.ddrissq.sigdosi;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@EnableConfigurationProperties(value = TestcontainersProperties.class)
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer postgresContainer(TestcontainersProperties props) {
        String imageName = "postgres:" + props.postgres().version();
        return new PostgreSQLContainer(
                DockerImageName.parse(imageName));
    }

}

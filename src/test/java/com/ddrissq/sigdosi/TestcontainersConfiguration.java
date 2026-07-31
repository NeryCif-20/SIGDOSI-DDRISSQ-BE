package com.ddrissq.sigdosi;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@EnableConfigurationProperties(value = TestcontainersProperties.class)
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer postgresContainer(TestcontainersProperties properties) {
        String imageName = "postgres:" + properties.getPostgres().getVersion();
        return new PostgreSQLContainer(
                DockerImageName.parse(imageName));
    }

    @Bean
    @ServiceConnection(name = "redis")
    GenericContainer<?> redisContainer(TestcontainersProperties properties) {
        String imageName = "redis:" + properties.getRedis().getVersion();
        GenericContainer<?> redis = new GenericContainer<>(
                DockerImageName.parse(imageName));
        redis.withExposedPorts(6379);
        return redis;
    }

}

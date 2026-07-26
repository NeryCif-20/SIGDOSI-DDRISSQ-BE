package com.ddrissq.sigdosi;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
@RequiredArgsConstructor
class TestcontainersConfiguration {

    private final TestcontainersProperties properties;

    @Bean
    @ServiceConnection
    PostgreSQLContainer postgresContainer() {
        String imageName = "postgres:" + properties.getPostgres().getVersion();
        return new PostgreSQLContainer(
                DockerImageName.parse(imageName));
    }

    @Bean
    @ServiceConnection(name = "redis")
    GenericContainer<?> redisContainer() {
        String imageName = "redis:" + properties.getRedis().getVersion();
        GenericContainer<?> redis = new GenericContainer<>(
                DockerImageName.parse(imageName));
        redis.withExposedPorts(6379);
        return redis;
    }

}

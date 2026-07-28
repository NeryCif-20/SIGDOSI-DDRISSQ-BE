package com.ddrissq.sigdosi.iam.permission.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.testcontainers.utility.TestcontainersConfiguration;

@RequiredArgsConstructor
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
public class PermissionRepositoryTest {

    private final PermissionRepository repository;



}

package com.ddrissq.sigdosi.iam.permission.specification;

import com.ddrissq.sigdosi.TestcontainersConfiguration;
import com.ddrissq.sigdosi.iam.permission.model.Permission;
import com.ddrissq.sigdosi.iam.permission.repository.PermissionRepository;
import com.ddrissq.sigdosi.iam.permission.support.PermissionTestData;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@DataJpaTest
@Import(TestcontainersConfiguration.class)
class PermissionSpecificationTest {

    private final PermissionRepository repository;

    @Test
    @DisplayName("Filtra ignorando mayúsculas y espacios cuando se proporciona un módulo válido")
    void givenValidModuleWithSpacesAndLowerCase_whenFindAll_thenReturnsMatchingPermissions() {
        // Given
        String moduleFilter = " " + MODULE.toLowerCase() + " ";
        List<Permission> expectedPermissions = repository.saveAll(
                PermissionTestData.generateForModule(MODULE));
        repository.saveAll(PermissionTestData.generateForModule(DIFFERENT_MODULE));
        Specification<Permission> spec = PermissionSpecification.hasModule(moduleFilter);
        // When
        List<Permission> permissions = repository.findAll(spec);
        // Then
        assertThat(permissions).containsExactlyInAnyOrderElementsOf(expectedPermissions);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    @DisplayName("Devuelve todos los registros cuando el filtro de módulo es nulo o blanco")
    void givenNullOrBlankModule_whenFindAll_thenReturnsAllPermissions(String moduleFilter) {
        // Given
        List<Permission> expectedPermissions = repository.saveAll(
                PermissionTestData.generateForModule(MODULE));
        Specification<Permission> spec = PermissionSpecification.hasModule(moduleFilter);
        // When
        List<Permission> permissions = repository.findAll(spec);
        // Then
        assertThat(permissions).containsExactlyInAnyOrderElementsOf(expectedPermissions);
    }

    @Test
    @DisplayName("Devuelve lista vacía cuando ningún módulo coincide con el filtro")
    void givenNonMatchingModule_whenFindAll_thenReturnsEmptyList() {
        // Given
        repository.saveAll(PermissionTestData.generateForModule(MODULE));
        Specification<Permission> spec = PermissionSpecification.hasModule(DIFFERENT_MODULE);
        // When
        List<Permission> permissions = repository.findAll(spec);
        // Then
        assertThat(permissions).isEmpty();
    }

    @Test
    @DisplayName("Filtra correctamente por la acción especificada cuando existe coincidencia")
    void givenMatchingAction_whenFindAll_thenReturnsMatchingPermissions() {
        // Given
        List<Permission> expectedPermissions = repository.saveAll(
                PermissionTestData.generateForAction(ACTION));
        repository.saveAll(PermissionTestData.generateForAction(DIFFERENT_ACTION));
        Specification<Permission> spec = PermissionSpecification.hasAction(ACTION);
        // When
        List<Permission> permissions = repository.findAll(spec);
        // Then
        assertThat(permissions).containsExactlyInAnyOrderElementsOf(expectedPermissions);
    }

    @Test
    @DisplayName("Devuelve todos los registros cuando el filtro de acción es nulo")
    void givenNullAction_whenFindAll_thenReturnsAllPermissions() {
        // Given
        List<Permission> expectedPermissions = repository.saveAll(
                PermissionTestData.generateForAction(ACTION));
        Specification<Permission> spec = PermissionSpecification.hasAction(null);
        // When
        List<Permission> permissions = repository.findAll(spec);
        // Then
        assertThat(permissions).containsExactlyInAnyOrderElementsOf(expectedPermissions);
    }

    @Test
    @DisplayName("Devuelve lista vacía cuando ninguna entidad coincide con la acción especificada")
    void givenNonMatchingAction_whenFindAll_thenReturnsEmptyList() {
        // Given
        repository.saveAll(PermissionTestData.generateForAction(ACTION));
        Specification<Permission> spec = PermissionSpecification.hasAction(DIFFERENT_ACTION);
        // When
        List<Permission> permissions = repository.findAll(spec);
        // Then
        assertThat(permissions).isEmpty();
    }

    @Test
    @DisplayName("Filtra correctamente por módulo y acción cuando ambos criterios coinciden")
    void givenModuleAndAction_whenFindAll_thenReturnsMatchingPermission() {
        // Given
        Permission expectedPermission = repository.save(
                PermissionTestData.aPermission()
                        .build());
        repository.save(PermissionTestData.aPermission()
                .action(DIFFERENT_ACTION)
                .build());
        Specification<Permission> spec = Specification.allOf(
                PermissionSpecification.hasModule(MODULE),
                PermissionSpecification.hasAction(ACTION));
        // When
        List<Permission> permissions = repository.findAll(spec);
        // Then
        assertThat(permissions).containsExactly(expectedPermission);
    }

}

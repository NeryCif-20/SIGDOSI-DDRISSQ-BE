package com.ddrissq.sigdosi.iam.permission.repository;

import com.ddrissq.sigdosi.TestcontainersConfiguration;
import com.ddrissq.sigdosi.iam.permission.entity.Permission;
import com.ddrissq.sigdosi.iam.permission.support.PermissionTestData;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@DataJpaTest
@Import(value = TestcontainersConfiguration.class)
class PermissionRepositoryTest {

    private final PermissionRepository repository;

    private Permission existingPermission;

    @BeforeEach
    void setUp() {
        existingPermission = repository.save(
                PermissionTestData.aPermission().build());
    }

    @Test
    @DisplayName(value = "Devuelve true cuando existe un permiso por módulo y acción")
    void givenExistingPermission_whenExistsByModuleAndAction_thenReturnsTrue() {
        // When
        boolean exists = repository.existsByModuleAndAction(
                MODULE,
                ACTION);
        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName(value = "Devuelve false cuando el módulo no coincide")
    void givenDifferentModule_whenExistsByModuleAndAction_thenReturnsFalse() {
        // When
        boolean exists = repository.existsByModuleAndAction(
                DIFFERENT_MODULE,
                ACTION);
        // Then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName(value = "Devuelve false cuando la acción no coincide")
    void givenDifferentAction_whenExistsByModuleAndAction_thenReturnsFalse() {
        // When
        boolean exists = repository.existsByModuleAndAction(
                MODULE,
                DIFFERENT_ACTION);
        // Then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName(value = "Devuelve false cuando ni el módulo ni la acción coinciden")
    void givenDifferentModuleAndAction_whenExistsByModuleAndAction_thenReturnsFalse() {
        // When
        boolean exists = repository.existsByModuleAndAction(
                DIFFERENT_MODULE,
                DIFFERENT_ACTION);
        // Then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName(value = "Devuelve true cuando existe otro permiso con igual módulo y acción pero ID distinto")
    void givenPermissionExistsAndDifferentExcludedId_whenExistsByModuleAndActionAndIdNot_thenReturnsTrue() {
        // When
        boolean exists = repository.existsByModuleAndActionAndIdNot(
                MODULE,
                ACTION,
                ID);
        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName(value = "Devuelve false cuando el único permiso coincidente coincide con el ID excluido")
    void givenSameExcludedId_whenExistsByModuleAndActionAndIdNot_thenReturnsFalse() {
        // When
        boolean exists = repository.existsByModuleAndActionAndIdNot(
                MODULE,
                ACTION,
                existingPermission.getId());
        // Then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName(value = "Devuelve false al buscar excluyendo un ID si el módulo no coincide")
    void givenDifferentModule_whenExistsByModuleAndActionAndIdNot_thenReturnsFalse() {
        // When
        boolean exists = repository.existsByModuleAndActionAndIdNot(
                DIFFERENT_MODULE,
                ACTION,
                ID);
        // Then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName(value = "Devuelve false al buscar excluyendo un ID si la acción no coincide")
    void givenDifferentAction_whenExistsByModuleAndActionAndIdNot_thenReturnsFalse() {
        // When
        boolean exists = repository.existsByModuleAndActionAndIdNot(
                MODULE,
                DIFFERENT_ACTION,
                ID);
        // Then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName(value = "Devuelve false al buscar excluyendo un ID si ni el módulo ni la acción coinciden")
    void givenDifferentModuleAndAction_whenExistsByModuleAndActionAndIdNot_thenReturnsFalse() {
        // When
        boolean exists = repository.existsByModuleAndActionAndIdNot(
                DIFFERENT_MODULE,
                DIFFERENT_ACTION,
                ID);
        // Then
        assertThat(exists).isFalse();
    }

}
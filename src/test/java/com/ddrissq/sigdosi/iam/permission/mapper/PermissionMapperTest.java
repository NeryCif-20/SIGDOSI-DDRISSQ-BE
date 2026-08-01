package com.ddrissq.sigdosi.iam.permission.mapper;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionCreateRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionUpdateRequest;
import com.ddrissq.sigdosi.iam.permission.model.Permission;
import com.ddrissq.sigdosi.iam.permission.support.PermissionCreateRequestTestData;
import com.ddrissq.sigdosi.iam.permission.support.PermissionTestData;
import com.ddrissq.sigdosi.iam.permission.support.PermissionUpdateRequestTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

class PermissionMapperTest {

    private final PermissionMapper mapper = new PermissionMapperImpl();

    @Test
    @DisplayName(value = "Mapea una solicitud de creación a una entidad Permission")
    void givenCreateRequest_whenToPermission_thenMapsAllFields() {
        // Given
        PermissionCreateRequest request = PermissionCreateRequestTestData.aRequest()
                .build();
        // When
        Permission permission = mapper.toPermission(request);
        // Then
        assertThat(permission.getModule()).isEqualTo(request.module());
        assertThat(permission.getAction()).isEqualTo(request.action());
    }

    @Test
    @DisplayName(value = "Mapea una solicitud de creación con valores nulos a una entidad Permission")
    void givenCreateRequestWithNullValues_whenToPermission_thenMapsNullValues() {
        // Given
        PermissionCreateRequest request = PermissionCreateRequestTestData.aRequest()
                .action(null)
                .module(null)
                .build();
        // When
        Permission permission = mapper.toPermission(request);
        // Then
        assertThat(permission.getModule()).isNull();
        assertThat(permission.getAction()).isNull();
    }

    @Test
    @DisplayName(value = "Mapea una solicitud de creación nula a a una entidad nula")
    void givenNullCreateRequest_whenToPermission_thenReturnsNull() {
        // When
        Permission result = mapper.toPermission(null);
        // Then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName(value = "Mapea una entidad Permission a su DTO de respuesta")
    void givenPermissionEntity_whenToResponse_thenMapsAllFields() {
        // Given
        Permission permission = PermissionTestData.aPermission()
                .build();
        permission.setId(ID);
        // When
        PermissionResponse response = mapper.toResponse(permission);
        // Then
        assertThat(response.id()).isEqualTo(permission.getId());
        assertThat(response.module()).isEqualTo(permission.getModule());
        assertThat(response.action()).isEqualTo(permission.getAction());
    }

    @Test
    @DisplayName(value = "Mapea una entidad Permission nula a su DTO de respuesta")
    void givenNullPermissionEntity_whenToResponse_thenReturnsNull() {
        // When
        PermissionResponse response = mapper.toResponse(null);
        // Then
        assertThat(response).isNull();
    }

    @Test
    @DisplayName(value = "Actualiza todos los campos de la entidad cuando se proporcionan todos los datos")
    void givenUpdateRequestWithAllFields_whenUpdatePermission_thenUpdatesAllFields() {
        // Given
        Permission permission = PermissionTestData.aPermission()
                .module(DIFFERENT_MODULE)
                .action(DIFFERENT_ACTION)
                .build();
        PermissionUpdateRequest request = PermissionUpdateRequestTestData.aRequest()
                .module(MODULE)
                .action(ACTION)
                .build();
        // When
        mapper.updatePermission(request, permission);
        // Then
        assertThat(permission.getModule()).isEqualTo(request.module());
        assertThat(permission.getAction()).isEqualTo(request.action());
    }

    @Test
    @DisplayName(value = "Actualiza solo los campos no nulos recibidos en la solicitud")
    void givenUpdateRequestWithPartialFields_whenUpdatePermission_thenUpdatesOnlyNonNullFields() {
        // Given
        Permission permission = PermissionTestData.aPermission()
                .module(DIFFERENT_MODULE)
                .build();
        PermissionUpdateRequest request = PermissionUpdateRequestTestData.aRequest()
                .module(MODULE)
                .build();
        // When
        mapper.updatePermission(request, permission);
        // Then
        assertThat(permission.getModule()).isEqualTo(request.module());
        assertThat(permission.getAction()).isEqualTo(ACTION);
    }

    @Test
    @DisplayName(value = "No modifica la entidad cuando todos los campos de la solicitud son nulos")
    void givenUpdateRequestWithNullFields_whenUpdatePermission_thenDoesNotModifyPermission() {
        // Given
        Permission permission = PermissionTestData.aPermission()
                .build();
        PermissionUpdateRequest request = PermissionUpdateRequestTestData.aRequest()
                .build();
        // When
        mapper.updatePermission(request, permission);
        // Then
        assertThat(permission.getModule()).isEqualTo(MODULE);
        assertThat(permission.getAction()).isEqualTo(ACTION);
    }

    @Test
    @DisplayName(value = "No modifica la entidad cuando la solicitud es nula")
    void givenNullUpdateRequest_whenUpdatingPermission_thenKeepsExistingValues () {
        // Given
        Permission permission = PermissionTestData.aPermission()
                .build();
        // When
        mapper.updatePermission(null, permission);
        // Then
        assertThat(permission.getModule()).isEqualTo(MODULE);
        assertThat(permission.getAction()).isEqualTo(ACTION);
    }

}
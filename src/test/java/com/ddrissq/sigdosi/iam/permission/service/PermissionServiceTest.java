package com.ddrissq.sigdosi.iam.permission.service;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionCreateRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionSearchRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionUpdateRequest;
import com.ddrissq.sigdosi.iam.permission.exception.PermissionExceptionMessages;
import com.ddrissq.sigdosi.iam.permission.mapper.PermissionMapper;
import com.ddrissq.sigdosi.iam.permission.entity.Permission;
import com.ddrissq.sigdosi.iam.permission.repository.PermissionRepository;
import com.ddrissq.sigdosi.iam.permission.support.*;
import com.ddrissq.sigdosi.shared.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.shared.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
class PermissionServiceTest {

    @Mock
    private PermissionRepository repository;

    @Mock
    private PermissionMapper mapper;

    @InjectMocks
    private PermissionServiceImpl service;

    @Test
    @DisplayName(value = "Devuelve la respuesta del permiso cuando existe el id buscado")
    void givenExistingId_whenGet_thenReturnPermissionResponse() {
        // Given
        Permission permission = PermissionTestData.aPermission()
                .build();
        permission.setId(ID);
        PermissionResponse expectedResponse = PermissionResponseTestData.aResponse()
                .build();
        given(repository.findById(ID)).willReturn(Optional.of(permission));
        given(mapper.toResponse(permission)).willReturn(expectedResponse);
        // When
        PermissionResponse response = service.get(ID);
        // Then
        assertThat(response).isEqualTo(expectedResponse);
        verify(repository).findById(ID);
        verify(mapper).toResponse(permission);
    }

    @Test
    @DisplayName(value = "Lanza EntityNotFoundException cuando el permiso no existe")
    void givenNonExistingId_whenGet_thenThrowsEntityNotFoundException() {
        // Given
        given(repository.findById(ID)).willReturn(Optional.empty());
        // When + Then
        assertThatThrownBy(() -> service.get(ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(PermissionExceptionMessages.NOT_FOUND);
        verify(repository).findById(ID);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName(value = "Crea y devuelve un permiso correctamente cuando el módulo y la acción son únicos")
    void givenValidPermissionCreateRequest_whenCreate_thenReturnsPermissionResponse() {
        // Given
        PermissionCreateRequest request = PermissionCreateRequestTestData.aRequest()
                .build();
        Permission permission = PermissionTestData.aPermission()
                .build();
        Permission savedPermission = PermissionTestData.aPermission()
                .build();
        savedPermission.setId(ID);
        PermissionResponse expectedResponse = PermissionResponseTestData.aResponse()
                .build();
        given(repository.existsByModuleAndAction(
                request.module(), request.action())).willReturn(false);
        given(mapper.toPermission(request)).willReturn(permission);
        given(repository.save(permission)).willReturn(savedPermission);
        given(mapper.toResponse(savedPermission)).willReturn(expectedResponse);
        // When
        PermissionResponse response = service.create(request);
        // Then
        assertThat(response).isEqualTo(expectedResponse);
        verify(repository).existsByModuleAndAction(request.module(), request.action());
        verify(mapper).toPermission(request);
        verify(repository).save(permission);
        verify(mapper).toResponse(savedPermission);
    }

    @Test
    @DisplayName(value = "Lanza EntityAlreadyExistsException cuando ya existe un permiso con el mismo módulo y acción")
    void givenDuplicateModuleAndAction_whenCreate_thenThrowsEntityAlreadyExistsException() {
        // Given
        PermissionCreateRequest request = PermissionCreateRequestTestData.aRequest()
                .build();
        given(repository.existsByModuleAndAction(
                request.module(), request.action())).willReturn(true);
        // When + Then
        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(EntityAlreadyExistsException.class)
                .hasMessage(PermissionExceptionMessages.ALREADY_EXISTS);
        verify(repository).existsByModuleAndAction(request.module(), request.action());
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName(value = "Actualiza y devuelve el permiso cuando los datos son válidos y únicos")
    void givenValidUpdateRequest_whenUpdate_thenReturnsUpdatedPermissionResponse() {
        // Given
        PermissionUpdateRequest request = PermissionUpdateRequestTestData.aRequest()
                .module(MODULE)
                .action(ACTION)
                .build();
        Permission permission = PermissionTestData.aPermission()
                .module(DIFFERENT_MODULE)
                .action(DIFFERENT_ACTION)
                .build();
        permission.setId(ID);
        PermissionResponse expectedResponse = PermissionResponseTestData.aResponse()
                .build();
        given(repository.findById(ID)).willReturn(Optional.of(permission));
        given(repository.existsByModuleAndActionAndIdNot(
                request.module(), request.action(), ID)).willReturn(false);
        given(mapper.toResponse(permission)).willReturn(expectedResponse);
        // When
        PermissionResponse response = service.update(ID, request);
        // Then
        assertThat(response).isEqualTo(expectedResponse);
        verify(repository).findById(ID);
        verify(repository).existsByModuleAndActionAndIdNot(
                request.module(), request.action(), ID);
        verify(mapper).updatePermission(request, permission);
        verify(mapper).toResponse(permission);
    }

    @Test
    @DisplayName(value = "Usa los valores actuales del permiso para validar cuando la solicitud tiene campos nulos")
    void givenRequestWithNullFields_whenUpdate_thenValidatesUsingExistingPermissionValues() {
        // Given
        PermissionUpdateRequest request = PermissionUpdateRequestTestData.aRequest()
                .build();
        Permission permission = PermissionTestData.aPermission()
                .build();
        permission.setId(ID);
        PermissionResponse expectedResponse = PermissionResponseTestData.aResponse()
                .build();
        given(repository.findById(ID)).willReturn(Optional.of(permission));
        given(repository.existsByModuleAndActionAndIdNot(
                permission.getModule(), permission.getAction(), ID)).willReturn(false);
        given(mapper.toResponse(permission)).willReturn(expectedResponse);
        // When
        PermissionResponse response = service.update(ID, request);
        // Then
        assertThat(response).isEqualTo(expectedResponse);
        verify(repository).findById(ID);
        verify(repository).existsByModuleAndActionAndIdNot(
                permission.getModule(), permission.getAction(), ID);
        verify(mapper).updatePermission(request, permission);
        verify(mapper).toResponse(permission);
    }

    @Test
    @DisplayName(value = "Lanza EntityNotFoundException cuando el permiso a actualizar no existe")
    void givenNonExistingId_whenUpdate_thenThrowsEntityNotFoundException() {
        // Given
        PermissionUpdateRequest request = PermissionUpdateRequestTestData.aRequest()
                .build();
        given(repository.findById(ID)).willReturn(Optional.empty());
        // When + Then
        assertThatThrownBy(() -> service.update(ID, request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(PermissionExceptionMessages.NOT_FOUND);
        verify(repository).findById(ID);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName(value = "Usa el módulo existente y la acción del request cuando solo el módulo es nulo")
    void givenRequestWithNullModule_whenUpdate_thenValidatesUsingExistingModuleAndRequestAction() {
        // Given
        PermissionUpdateRequest request = PermissionUpdateRequestTestData.aRequest()
                .action(ACTION)
                .build();
        Permission permission = PermissionTestData.aPermission()
                .action(DIFFERENT_ACTION)
                .build();
        permission.setId(ID);
        PermissionResponse expectedResponse = PermissionResponseTestData.aResponse()
                .build();
        given(repository.findById(ID)).willReturn(Optional.of(permission));
        given(repository.existsByModuleAndActionAndIdNot(
                permission.getModule(),request.action(), ID)).willReturn(false);
        given(mapper.toResponse(permission)).willReturn(expectedResponse);
        // When
        PermissionResponse response = service.update(ID, request);
        // Then
        assertThat(response).isEqualTo(expectedResponse);
        verify(repository).findById(ID);
        verify(repository).existsByModuleAndActionAndIdNot(
                permission.getModule(), request.action(), ID);
        verify(mapper).updatePermission(request, permission);
        verify(mapper).toResponse(permission);
    }

    @Test
    @DisplayName(value = "Usa la acción existente y el módulo del request cuando solo la acción es nula")
    void givenRequestWithNullAction_whenUpdate_thenValidatesUsingRequestModuleAndExistingAction() {
        // Given
        PermissionUpdateRequest request = PermissionUpdateRequestTestData.aRequest()
                .module(MODULE)
                .build();
        Permission permission = PermissionTestData.aPermission()
                .module(DIFFERENT_MODULE)
                .build();
        permission.setId(ID);
        PermissionResponse expectedResponse = PermissionResponseTestData.aResponse()
                .build();
        given(repository.findById(ID)).willReturn(Optional.of(permission));
        given(repository.existsByModuleAndActionAndIdNot(
                request.module(), permission.getAction(), ID)).willReturn(false);
        given(mapper.toResponse(permission)).willReturn(expectedResponse);
        // When
        PermissionResponse response = service.update(ID, request);
        // Then
        assertThat(response).isEqualTo(expectedResponse);
        verify(repository).findById(ID);
        verify(repository).existsByModuleAndActionAndIdNot(
                request.module(), permission.getAction(), ID);
        verify(mapper).updatePermission(request, permission);
        verify(mapper).toResponse(permission);
    }

    @Test
    @DisplayName(value = "Lanza EntityAlreadyExistsException cuando el módulo y acción ya existen en otro permiso")
    void givenDuplicateModuleAndAction_whenUpdate_thenThrowsEntityAlreadyExistsException() {
        // Given
        PermissionUpdateRequest request = PermissionUpdateRequestTestData.aRequest()
                .module(MODULE)
                .action(ACTION)
                .build();
        Permission permission = PermissionTestData.aPermission()
                .module(DIFFERENT_MODULE)
                .action(DIFFERENT_ACTION)
                .build();
        permission.setId(ID);
        given(repository.findById(ID)).willReturn(Optional.of(permission));
        given(repository.existsByModuleAndActionAndIdNot(
                request.module(), request.action(), ID)).willReturn(true);
        // When + Then
        assertThatThrownBy(() -> service.update(ID, request))
                .isInstanceOf(EntityAlreadyExistsException.class)
                .hasMessage(PermissionExceptionMessages.ALREADY_EXISTS);
        verify(repository).findById(ID);
        verify(repository)
                .existsByModuleAndActionAndIdNot(request.module(), request.action(), ID);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName(value = "Devuelve una página paginada de respuestas cuando existen permisos que coinciden con los filtros")
    void givenValidPageableAndSearchRequest_whenGetAll_thenReturnsPageOfPermissionResponses() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        PermissionSearchRequest request = PermissionSearchRequestTestData.aPermissionSearchRequest()
                .build();
        Permission permission = PermissionTestData.aPermission()
                .build();
        permission.setId(ID);
        PermissionResponse expectedResponse = PermissionResponseTestData.aResponse()
                .build();
        Page<Permission> expectedPage = new PageImpl<>(List.of(permission), pageable, 1);
        given(repository.findAll(
                ArgumentMatchers.<Specification<Permission>>any(), eq(pageable))).willReturn(expectedPage);
        given(mapper.toResponse(permission)).willReturn(expectedResponse);
        // When
        Page<PermissionResponse> response = service.getAll(request, pageable);
        // Then
        assertThat(response.getContent()).containsExactly(expectedResponse);
        verify(repository).findAll(ArgumentMatchers.<Specification<Permission>>any(), eq(pageable));
        verify(mapper).toResponse(permission);
    }

    @Test
    @DisplayName(value = "Devuelve una página vacía cuando ningún permiso coincide con los filtros de búsqueda")
    void givenSearchRequestWithNoMatches_whenGetAll_thenReturnsEmptyPage() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        PermissionSearchRequest request = PermissionSearchRequestTestData.aPermissionSearchRequest()
                .build();
        Page<Permission> expectedPage = Page.empty(pageable);
        given(repository.findAll(
                ArgumentMatchers.<Specification<Permission>>any(), eq(pageable))).willReturn(expectedPage);
        // When
        Page<PermissionResponse> response = service.getAll(request, pageable);
        // Then
        assertThat(response.getContent()).isEmpty();
        verify(repository).findAll(
                ArgumentMatchers.<Specification<Permission>>any(), eq(pageable));
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName(value = "Devuelve la entidad Permission cuando existe el ID buscado")
    void givenExistingId_whenFindById_thenReturnsPermission() {
        // Given
        Permission expectedPermission = PermissionTestData.aPermission()
                .build();
        expectedPermission.setId(ID);
        given(repository.findById(ID)).willReturn(Optional.of(expectedPermission));
        // When
        Permission permission = service.getByIdOrThrow(ID);
        // Then
        assertThat(permission).isEqualTo(expectedPermission);
        verify(repository).findById(ID);
    }

    @Test
    @DisplayName(value = "Lanza EntityNotFoundException cuando el permiso no existe")
    void givenNonExistingId_whenFindById_thenThrowsEntityNotFoundException() {
        // Given
        given(repository.findById(ID)).willReturn(Optional.empty());
        // When + Then
        assertThatThrownBy(() -> service.getByIdOrThrow(ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(PermissionExceptionMessages.NOT_FOUND);
        verify(repository).findById(ID);
    }

    @Test
    @DisplayName(value = "Devuelve un conjunto inmodificable de permisos cuando existen los IDs proporcionados")
    void givenExistingIds_whenFindAllById_thenReturnsUnmodifiableSetOfPermissions() {
        // Given
        Set<UUID> ids = Set.of(ID, DIFFERENT_ID);
        Permission firstPermission = PermissionTestData.aPermission()
                .build();
        firstPermission.setId(ID);
        Permission secondPermission = PermissionTestData.aPermission()
                .build();
        secondPermission.setId(DIFFERENT_ID);
        given(repository.findAllById(ids)).willReturn(List.of(firstPermission, secondPermission));
        // When
        Set<Permission> permissions = service.getAllById(ids);
        // Then
        assertThat(permissions).containsExactlyInAnyOrder(firstPermission, secondPermission);
        assertThatThrownBy(() -> permissions.add(firstPermission))
                .isInstanceOf(UnsupportedOperationException.class);
        verify(repository).findAllById(ids);
    }

    @Test
    @DisplayName(value = "Devuelve un conjunto vacío cuando no se encuentran coincidencias para los IDs")
    void givenNonExistingIds_whenFindAllById_thenReturnsEmptySet() {
        // Given
        Set<UUID> ids = Set.of(ID, DIFFERENT_ID);
        given(repository.findAllById(ids)).willReturn(List.of());
        // When
        Set<Permission> permissions = service.getAllById(ids);
        // Then
        assertThat(permissions).isEmpty();
        verify(repository).findAllById(ids);
    }

}

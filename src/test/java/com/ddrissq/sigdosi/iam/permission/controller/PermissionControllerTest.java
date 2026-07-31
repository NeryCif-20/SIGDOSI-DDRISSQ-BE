package com.ddrissq.sigdosi.iam.permission.controller;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionCreateRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionSearchRequest;
import com.ddrissq.sigdosi.iam.permission.dto.PermissionUpdateRequest;
import com.ddrissq.sigdosi.iam.permission.exception.PermissionExceptionMessages;
import com.ddrissq.sigdosi.iam.permission.service.PermissionService;
import com.ddrissq.sigdosi.iam.permission.support.PermissionCreateRequestTestData;
import com.ddrissq.sigdosi.iam.permission.support.PermissionResponseTestData;
import com.ddrissq.sigdosi.iam.permission.support.PermissionUpdateRequestTestData;
import com.ddrissq.sigdosi.shared.exception.EntityAlreadyExistsException;
import com.ddrissq.sigdosi.shared.exception.EntityNotFoundException;
import com.ddrissq.sigdosi.shared.exception.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@RequiredArgsConstructor
@WebMvcTest(controllers = PermissionController.class)
class PermissionControllerTest {

    private static final String BASE_URL = "/v1/permissions";

    private final MockMvcTester mockMvcTester;
    private final ObjectMapper objectMapper;

    @MockitoBean
    private PermissionService service;

    @Test
    @DisplayName("Devuelve 200 OK y la respuesta del permiso cuando el ID existe")
    void givenExistingId_whenGet_thenReturns200AndPermissionResponse() {
        // Given
        PermissionResponse response = PermissionResponseTestData.aResponse()
                .build();
        given(service.get(ID)).willReturn(response);
        // When
        MvcTestResult result = mockMvcTester.get()
                .uri(BASE_URL + "/{id}", ID)
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.OK)
                .hasContentType(MediaType.APPLICATION_JSON);
        assertThat(result)
                .bodyJson()
                .convertTo(PermissionResponse.class)
                .isEqualTo(response);
        verify(service).get(ID);
    }

    @Test
    @DisplayName("Devuelve 404 NOT FOUND cuando el permiso buscado no existe")
    void givenNonExistingId_whenGet_thenReturns404NotFound() {
        // Given
        EntityNotFoundException exception = new EntityNotFoundException(
                PermissionExceptionMessages.NOT_FOUND);
        given(service.get(ID)).willThrow(exception);
        // When
        MvcTestResult result = mockMvcTester.get()
                .uri(BASE_URL + "/{id}", ID)
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.NOT_FOUND)
                .hasContentType(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(result)
                .bodyJson()
                .extractingPath("$.detail")
                .isEqualTo(PermissionExceptionMessages.NOT_FOUND);
        verify(service).get(ID);
    }

    @Test
    @DisplayName("Devuelve 400 BAD REQUEST cuando el formato del UUID en la URL es inválido")
    void givenInvalidUuidFormat_whenGet_thenReturns400BadRequest() throws Exception {
        // Given
        String invalidId = "135135-315135";
        // When
        MvcTestResult result = mockMvcTester.get()
                .uri(BASE_URL + "/{id}", invalidId)
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.BAD_REQUEST)
                .hasContentType(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(result)
                .bodyJson().extractingPath("$.detail")
                .isEqualTo(ExceptionMessages.invalidParameter("id"));
        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("Devuelve 201 CREATED y el permiso creado cuando los datos son válidos")
    void givenValidRequest_whenCreate_thenReturns201Created() {
        // Given
        PermissionCreateRequest request = PermissionCreateRequestTestData.aRequest()
                .build();
        PermissionResponse expectedResponse = PermissionResponseTestData.aResponse()
                .build();
        given(service.create(request)).willReturn(expectedResponse);
        // When
        MvcTestResult result = mockMvcTester.post()
                .uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.CREATED)
                .hasContentType(MediaType.APPLICATION_JSON);
        assertThat(result)
                .bodyJson()
                .convertTo(PermissionResponse.class)
                .isEqualTo(expectedResponse);
        verify(service).create(request);
    }

    @Test
    @DisplayName("Devuelve 404 BAD REQUEST cuando la validación @Valid del DTO de creación falla")
    void givenInvalidRequest_whenCreate_thenReturns400BadRequest() {
        // Given
        PermissionCreateRequest request = PermissionCreateRequestTestData.aRequest()
                .module(null)
                .build();
        // When
        MvcTestResult result = mockMvcTester.post()
                .uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.BAD_REQUEST)
                .hasContentType(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(result)
                .bodyJson()
                .extractingPath("$.detail")
                .isEqualTo(ExceptionMessages.VALIDATION_FAILED);
        assertThat(result)
                .bodyJson()
                .extractingPath("$.errors.module")
                .isNotNull()
                .isNotEmpty();
        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("Devuelve 409 CONFLICT cuando ya existe un permiso duplicado al crear")
    void givenDuplicateRequest_whenCreate_thenHandlesAlreadyExistsException() {
        // Given
        PermissionCreateRequest request = PermissionCreateRequestTestData.aRequest()
                .build();
        EntityAlreadyExistsException exception = new EntityAlreadyExistsException(
                PermissionExceptionMessages.ALREADY_EXISTS);
        given(service.create(request)).willThrow(exception);
        // When
        MvcTestResult result = mockMvcTester.post()
                .uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.CONFLICT)
                .hasContentType(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(result)
                .bodyJson()
                .extractingPath("$.detail")
                .isEqualTo(PermissionExceptionMessages.ALREADY_EXISTS);
        verify(service).create(request);
    }

    @Test
    @DisplayName("Devuelve 400 BAD REQUEST cuando el cuerpo de la solicitud contiene un JSON mal formado")
    void givenMalformedJson_whenCreate_thenReturns400BadRequest() {
        // Given
        String malformedJson = "{ invalid json ";
        // When
        MvcTestResult result = mockMvcTester.post()
                .uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedJson)
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.BAD_REQUEST)
                .hasContentType(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(result)
                .bodyJson()
                .extractingPath("$.detail")
                .isEqualTo(ExceptionMessages.INVALID_REQUEST_BODY);
        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("Devuelve 200 OK y el permiso actualizado cuando la solicitud de actualización es válida")
    void givenValidUpdateRequest_whenUpdate_thenReturns200Ok() {
        // Given
        PermissionUpdateRequest request = PermissionUpdateRequestTestData.aRequest()
                .build();
        PermissionResponse expectedResponse = PermissionResponseTestData.aResponse()
                .build();
        given(service.update(ID, request)).willReturn(expectedResponse);
        // When
        MvcTestResult result = mockMvcTester.patch()
                .uri(BASE_URL + "/{id}", ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.OK)
                .hasContentType(MediaType.APPLICATION_JSON);
        assertThat(result)
                .bodyJson()
                .convertTo(PermissionResponse.class)
                .isEqualTo(expectedResponse);
        verify(service).update(ID, request);
    }

    @Test
    @DisplayName("Devuelve 404 NOT FOUND al actualizar cuando el ID del permiso no existe")
    void givenNonExistingId_whenUpdate_thenReturns404NotFound() {
        // Given
        PermissionUpdateRequest request = PermissionUpdateRequestTestData.aRequest()
                .build();
        EntityNotFoundException exception = new EntityNotFoundException(
                PermissionExceptionMessages.NOT_FOUND);
        given(service.update(ID, request)).willThrow(exception);
        // When
        MvcTestResult result = mockMvcTester.patch()
                .uri(BASE_URL + "/{id}", ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.NOT_FOUND)
                .hasContentType(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(result)
                .bodyJson()
                .extractingPath("$.detail")
                .isEqualTo(PermissionExceptionMessages.NOT_FOUND);
        verify(service).update(ID, request);
    }

    @Test
    @DisplayName("Devuelve 409 CONFLICT al actualizar si el módulo y acción ya pertenecen a otro permiso")
    void givenDuplicateUpdateRequest_whenUpdate_thenReturns409Conflict() {
        // Given
        PermissionUpdateRequest request = PermissionUpdateRequestTestData.aRequest()
                .build();
        EntityAlreadyExistsException exception = new EntityAlreadyExistsException(
                PermissionExceptionMessages.ALREADY_EXISTS);
        given(service.update(ID, request)).willThrow(exception);
        // When
        MvcTestResult result = mockMvcTester.patch()
                .uri(BASE_URL + "/{id}", ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.CONFLICT)
                .hasContentType(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(result)
                .bodyJson()
                .extractingPath("$.detail")
                .isEqualTo(PermissionExceptionMessages.ALREADY_EXISTS);
        verify(service).update(ID, request);
    }

    @Test
    @DisplayName("Devuelve 405 METHOD NOT ALLOWED cuando se intenta actualizar un permiso con el método PUT")
    void givenPutRequest_whenUpdate_thenReturns405MethodNotAllowed() {
        // When
        MvcTestResult result = mockMvcTester.put()
                .uri(BASE_URL)
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.METHOD_NOT_ALLOWED)
                .hasContentType(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(result)
                .bodyJson()
                .extractingPath("$.detail")
                .isEqualTo(ExceptionMessages.METHOD_NOT_ALLOWED);
        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("Devuelve 204 NO CONTENT cuando se elimina un permiso existente")
    void givenExistingId_whenDelete_thenReturns204NoContent() {
        // When
        MvcTestResult result = mockMvcTester.delete()
                .uri(BASE_URL + "/{id}", ID)
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.NO_CONTENT);
        verify(service).delete(ID);
    }

    @Test
    @DisplayName("Devuelve 404 NOT FOUND cuando se intenta eliminar un permiso inexistente")
    void givenNonExistingId_whenDelete_thenReturns404NotFound() {
        // Given
        EntityNotFoundException exception = new EntityNotFoundException(
                PermissionExceptionMessages.NOT_FOUND);
        willThrow(exception).given(service).delete(ID);
        // When
        MvcTestResult result = mockMvcTester.delete()
                .uri(BASE_URL + "/{id}", ID)
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.NOT_FOUND)
                .hasContentType(MediaType.APPLICATION_PROBLEM_JSON);
        assertThat(result)
                .bodyJson()
                .extractingPath("$.detail")
                .isEqualTo(PermissionExceptionMessages.NOT_FOUND);
        verify(service).delete(ID);
    }

    @Test
    @DisplayName("Devuelve 200 OK y una página de permisos cuando existen registros que coinciden")
    void givenValidFilters_whenGetAll_thenReturns200AndPageWithContent() {
        // Given
        PermissionResponse response = PermissionResponseTestData.aResponse()
                .build();
        Page<PermissionResponse> expectedPage = new PageImpl<>(
                List.of(response), PageRequest.of(0, 10), 1);
        given(service.getAll(
                any(Pageable.class),
                any(PermissionSearchRequest.class)))
                .willReturn(expectedPage);
        // When
        MvcTestResult result = mockMvcTester.get()
                .uri(BASE_URL)
                .queryParam("page", "0")
                .queryParam("size", "10")
                .queryParam("module", MODULE)
                .queryParam("action", ACTION.toString())
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.OK)
                .hasContentType(MediaType.APPLICATION_JSON);
        assertThat(result)
                .bodyJson()
                .extractingPath("$.totalElements")
                .convertTo(InstanceOfAssertFactories.LONG)
                .isEqualTo(expectedPage.getTotalElements());
        assertThat(result)
                .bodyJson()
                .extractingPath("$.content")
                .convertTo(InstanceOfAssertFactories.list(PermissionResponse.class))
                .containsExactlyInAnyOrderElementsOf(expectedPage.getContent());
        verify(service).getAll(any(Pageable.class), any(PermissionSearchRequest.class));
    }

    @Test
    @DisplayName("Devuelve 200 OK y una página de permisos cuando no se envian filtros")
    void givenNoFilters_whenGetAll_thenReturns200Ok() {
        // Given
        PermissionResponse response = PermissionResponseTestData.aResponse()
                .build();
        Page<PermissionResponse> expectedPage = new PageImpl<>(
                List.of(response), Pageable.unpaged(), 1);
        given(service.getAll(
                any(Pageable.class),
                any(PermissionSearchRequest.class)))
                .willReturn(expectedPage);
        // When
        MvcTestResult result = mockMvcTester.get()
                .uri(BASE_URL)
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.OK)
                .hasContentType(MediaType.APPLICATION_JSON);
        assertThat(result)
                .bodyJson()
                .extractingPath("$.totalElements")
                .convertTo(InstanceOfAssertFactories.LONG)
                .isEqualTo(expectedPage.getTotalElements());
        assertThat(result)
                .bodyJson()
                .extractingPath("$.content")
                .convertTo(InstanceOfAssertFactories.list(PermissionResponse.class))
                .containsExactlyInAnyOrderElementsOf(expectedPage.getContent());
        verify(service).getAll(any(Pageable.class), any(PermissionSearchRequest.class));
    }

    @Test
    @DisplayName("Devuelve 200 OK y una página vacía cuando ningún permiso coincide con los filtros")
    void givenNoMatches_whenGetAll_thenReturns200AndEmptyPage() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<PermissionResponse> expectedPage = Page.empty(pageable);
        given(service.getAll(
                any(Pageable.class),
                any(PermissionSearchRequest.class)))
                .willReturn(expectedPage);
        // When
        MvcTestResult result = mockMvcTester.get()
                .uri(BASE_URL)
                .queryParam("page", "0")
                .queryParam("size", "10")
                .queryParam("module", MODULE)
                .with(jwt())
                .exchange();
        // Then
        assertThat(result)
                .hasStatus(HttpStatus.OK)
                .hasContentType(MediaType.APPLICATION_JSON);
        assertThat(result)
                .bodyJson()
                .extractingPath("$.totalElements")
                .convertTo(InstanceOfAssertFactories.LONG)
                .isEqualTo(expectedPage.getTotalElements());
        assertThat(result)
                .bodyJson()
                .extractingPath("$.content")
                .convertTo(InstanceOfAssertFactories.list(PermissionResponse.class))
                .isEmpty();
        verify(service).getAll(any(Pageable.class), any(PermissionSearchRequest.class));
    }

}

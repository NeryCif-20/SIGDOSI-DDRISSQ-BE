package com.ddrissq.sigdosi.shared.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionMessageTest {

    @Test
    @DisplayName(value = "Devuelve el mensaje esperado cuando se proporciona un nombre de parámetro válido")
    void givenParameterName_whenBuildingInvalidParameterMessage_thenReturnsExpectedMessage() {
        // Given
        String parameter = "id";
        // When
        String result = ExceptionMessages.invalidParameter(parameter);
        // Then
        assertThat(result).isEqualTo("El parametro '" + parameter + "' es invalido");
    }

}

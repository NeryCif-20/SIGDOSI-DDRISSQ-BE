package com.ddrissq.sigdosi.iam.permission.model;

import com.ddrissq.sigdosi.iam.permission.support.PermissionTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.ACTION;
import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.MODULE;
import static org.assertj.core.api.Assertions.assertThat;

public class PermissionTest {

    @Test
    @DisplayName(value = "Devuelve la autoridad compuesta por el módulo y la acción")
    void givenPermission_whenGetAuthority_thenReturnsModuleAndActionAsAuthority() {
        // Given
        Permission permission = PermissionTestData.aPermission()
                .build();
        // When
        String authority = permission.getAuthority();
        // Then
        assertThat(authority)
                .isEqualTo(MODULE + "_" + ACTION);
    }

}

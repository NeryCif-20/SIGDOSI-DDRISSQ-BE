package com.ddrissq.sigdosi.iam.permission.support;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PermissionResponseTestData {

    public static PermissionResponse.PermissionResponseBuilder aResponse() {
        return PermissionResponse.builder()
                .id(ID)
                .module(MODULE)
                .action(ACTION);
    }

}
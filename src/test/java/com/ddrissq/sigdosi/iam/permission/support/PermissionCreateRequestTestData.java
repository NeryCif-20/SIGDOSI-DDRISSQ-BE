package com.ddrissq.sigdosi.iam.permission.support;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionCreateRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.ACTION;
import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.MODULE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PermissionCreateRequestTestData {

    public static PermissionCreateRequest.PermissionCreateRequestBuilder aRequest() {
        return PermissionCreateRequest.builder()
                .module(MODULE)
                .action(ACTION);
    }

}

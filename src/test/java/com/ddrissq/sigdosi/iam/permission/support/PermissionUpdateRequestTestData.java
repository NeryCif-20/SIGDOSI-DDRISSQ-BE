package com.ddrissq.sigdosi.iam.permission.support;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionUpdateRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PermissionUpdateRequestTestData {

    public static PermissionUpdateRequest.PermissionUpdateRequestBuilder aRequest() {
        return PermissionUpdateRequest.builder();
    }

}

package com.ddrissq.sigdosi.iam.permission.support;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionSearchRequest;

import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.ACTION;
import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.MODULE;

public final class PermissionSearchRequestTestData {

    public static PermissionSearchRequest.PermissionSearchRequestBuilder aPermissionSearchRequest() {
        return PermissionSearchRequest.builder()
                .module(MODULE)
                .action(ACTION);
    }

}

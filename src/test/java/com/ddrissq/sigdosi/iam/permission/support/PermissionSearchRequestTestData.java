package com.ddrissq.sigdosi.iam.permission.support;

import com.ddrissq.sigdosi.iam.permission.dto.PermissionSearchRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.ACTION;
import static com.ddrissq.sigdosi.iam.permission.support.PermissionTestConstants.MODULE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PermissionSearchRequestTestData {

    public static PermissionSearchRequest.PermissionSearchRequestBuilder aPermissionSearchRequest() {
        return PermissionSearchRequest.builder()
                .search(MODULE)
                .actions(Set.of(ACTION));
    }

}

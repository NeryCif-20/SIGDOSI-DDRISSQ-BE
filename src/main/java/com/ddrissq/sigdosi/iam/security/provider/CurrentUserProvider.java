package com.ddrissq.sigdosi.iam.security.provider;

import java.util.List;
import java.util.UUID;

public interface CurrentUserProvider {

    UUID getUserId();
    List<String> getAuthorities();

}

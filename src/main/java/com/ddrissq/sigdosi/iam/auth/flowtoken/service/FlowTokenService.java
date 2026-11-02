package com.ddrissq.sigdosi.iam.auth.flowtoken.service;

import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowToken;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowTokenCreateResult;
import com.ddrissq.sigdosi.iam.user.model.User;

public interface FlowTokenService {

    FlowTokenCreateResult create(User user);
    FlowToken getByTokenOrThrow(String token);
    void deleteAllExpiredTokens();

}

package com.ddrissq.sigdosi.iam.auth.flowtoken.service;

import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowToken;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowTokenResult;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowTokenStep;
import com.ddrissq.sigdosi.iam.user.model.User;

public interface FlowTokenService {

    FlowTokenResult create(User user, FlowTokenStep step);
    FlowToken getByTokenOrThrow(String token, FlowTokenStep expectedStep);
    void deleteAllExpiredTokens();


}

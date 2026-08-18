package com.ddrissq.sigdosi.iam.auth.flowtoken.service;

import com.ddrissq.sigdosi.iam.auth.flowtoken.entity.FlowToken;
import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowStep;
import com.ddrissq.sigdosi.iam.user.entity.UserAccount;

public interface FlowTokenService {

    String create(UserAccount user, FlowStep step);

    FlowToken getByTokenOrThrow(String token, FlowStep expectedStep);

}

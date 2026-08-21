package com.ddrissq.sigdosi.iam.security.jwt.service;

import com.ddrissq.sigdosi.iam.security.jwt.model.JwtGenerateData;

public interface JwtService {

    String generate(JwtGenerateData data);

}

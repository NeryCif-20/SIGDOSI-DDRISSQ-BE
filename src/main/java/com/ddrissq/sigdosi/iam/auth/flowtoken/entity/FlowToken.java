package com.ddrissq.sigdosi.iam.auth.flowtoken.entity;

import com.ddrissq.sigdosi.iam.auth.flowtoken.model.FlowStep;
import com.ddrissq.sigdosi.iam.user.entity.UserAccount;
import com.ddrissq.sigdosi.common.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FlowToken extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount user;
    private String tokenHash;
    @Enumerated(value = EnumType.STRING)
    private FlowStep step;
    private Instant revokedAt;
    private Instant expiresAt;

}

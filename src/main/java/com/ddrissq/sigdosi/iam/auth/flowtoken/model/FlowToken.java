package com.ddrissq.sigdosi.iam.auth.flowtoken.model;

import com.ddrissq.sigdosi.iam.user.model.User;
import com.ddrissq.sigdosi.common.model.AbstractEntity;
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
    private User user;
    private String tokenHash;
    @Enumerated(value = EnumType.STRING)
    private FlowTokenStep step;
    private Instant revokedAt;
    private Instant expiresAt;

}

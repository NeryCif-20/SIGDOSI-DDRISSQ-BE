package com.ddrissq.sigdosi.iam.auth.passwordtoken.entity;

import com.ddrissq.sigdosi.iam.auth.passwordtoken.model.PasswordTokenPurpose;
import com.ddrissq.sigdosi.iam.user.entity.UserAccount;
import com.ddrissq.sigdosi.shared.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PasswordToken extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount user;
    private String tokenHash;
    @Enumerated(value = EnumType.STRING)
    private PasswordTokenPurpose purpose;
    private Instant revokedAt;
    private Instant expiresAt;

    public boolean isRevoked() {
        return this.revokedAt != null;
    }

    public boolean isExpired() {
        return this.expiresAt.isBefore(Instant.now());
    }

}

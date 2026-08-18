package com.ddrissq.sigdosi.iam.user.entity;

import com.ddrissq.sigdosi.iam.role.entity.Role;
import com.ddrissq.sigdosi.iam.user.model.UserAccountStatus;
import com.ddrissq.sigdosi.shared.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserAccount extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private String email;
    private String passwordHash;
    @Enumerated(value = EnumType.STRING)
    private UserAccountStatus status;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile profile;

    public void setUserProfile(UserProfile profile) {
        this.profile = profile;
        profile.setUser(this);
    }

}

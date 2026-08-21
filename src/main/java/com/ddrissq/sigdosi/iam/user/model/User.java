package com.ddrissq.sigdosi.iam.user.model;

import com.ddrissq.sigdosi.iam.role.model.Role;
import com.ddrissq.sigdosi.common.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_account")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private String email;
    private String passwordHash;
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile profile;

    public void setProfile(UserProfile profile) {
        this.profile = profile;
        profile.setUser(this);
    }

}

package com.ddrissq.sigdosi.iam.user.entity;

import com.ddrissq.sigdosi.common.entity.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserProfile extends AbstractEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount user;
    private String cui;
    private String firstName;
    private String lastName;
    private String avatar;
    private String phoneNumber;

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

}

package com.ddrissq.sigdosi.iam.permission.model;

import com.ddrissq.sigdosi.common.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Permission extends AbstractEntity {

    private String module;
    @Enumerated(value = EnumType.STRING)
    private PermissionAction action;

    public String getAuthority() {
        return this.module + "_" + this.action;
    }

}

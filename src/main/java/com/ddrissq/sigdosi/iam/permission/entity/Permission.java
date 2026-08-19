package com.ddrissq.sigdosi.iam.permission.entity;

import com.ddrissq.sigdosi.iam.permission.model.PermissionAction;
import com.ddrissq.sigdosi.common.entity.AbstractEntity;
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

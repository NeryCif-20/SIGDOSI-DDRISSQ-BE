package com.ddrissq.sigdosi.iam.permission.model;

import com.ddrissq.sigdosi.shared.model.AbstractModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Permission extends AbstractModel {

    private String module;
    @Enumerated(value = EnumType.STRING)
    private PermissionAction action;

}

package com.ddrissq.sigdosi.iam.permission.model;

import com.ddrissq.sigdosi.shared.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Permission extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String module;
    @Enumerated(value = EnumType.STRING)
    private PermissionAction action;

}

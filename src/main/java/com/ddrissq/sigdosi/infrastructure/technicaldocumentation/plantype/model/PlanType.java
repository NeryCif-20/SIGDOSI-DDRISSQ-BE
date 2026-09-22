package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.model;

import com.ddrissq.sigdosi.common.persistence.model.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PlanType extends BaseEntity {

    private String code;
    private String name;
    private String description;

}

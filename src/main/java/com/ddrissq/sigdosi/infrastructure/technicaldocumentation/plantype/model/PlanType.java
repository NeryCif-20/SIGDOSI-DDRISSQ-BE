package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.model;

import com.ddrissq.sigdosi.common.model.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PlanType extends AbstractEntity {

    private String code;
    private String name;
    private String description;

}

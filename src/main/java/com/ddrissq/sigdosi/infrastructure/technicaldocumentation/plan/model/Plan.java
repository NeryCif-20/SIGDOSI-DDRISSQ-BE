package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plan.model;

import com.ddrissq.sigdosi.common.persistence.model.BaseEntity;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import com.ddrissq.sigdosi.infrastructure.technicaldocumentation.plantype.model.PlanType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Plan extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "plan_type_id")
    private PlanType type;
    @ManyToOne
    @JoinColumn(name = "health_facility_id")
    private HealthFacility healthFacility;
    String path;
    Short version;
    String notes;

}

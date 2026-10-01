package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.image.model;

import com.ddrissq.sigdosi.common.persistence.model.BaseEntity;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class HealthFacilityImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_facility_id")
    private HealthFacility healthFacility;
    private String path;

}

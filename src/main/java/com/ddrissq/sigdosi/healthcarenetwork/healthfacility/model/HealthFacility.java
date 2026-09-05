package com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model;

import com.ddrissq.sigdosi.common.model.AbstractEntity;
import com.ddrissq.sigdosi.healthcarenetwork.community.model.Community;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacilitytype.model.HealthFacilityType;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class HealthFacility extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;
    @ManyToOne
    @JoinColumn(name = "health_facility_type_id")
    private HealthFacilityType healthFacilityType;
    private Boolean isHeadquarters;
    @Enumerated(value = EnumType.STRING)
    private HealthFacilityStatus status;
    private BigDecimal totalLandArea;
    private BigDecimal buildingFootprint;
    private BigDecimal availableExpansionArea;
    @Enumerated(value = EnumType.STRING)
    private PropertyTenure propertyTenure;
    @Enumerated(value = EnumType.STRING)
    private PropertyStatus propertyStatus;
    private String notes;
    @Column(columnDefinition = "geography(Point,4326)")
    private Point location;

}

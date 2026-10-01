package com.ddrissq.sigdosi.infrastructure.technicaldocumentation.facilityelement.model;

import com.ddrissq.sigdosi.common.persistence.model.BaseEntity;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.model.BuildingElement;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.model.BuildingMaterial;
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
public class FacilityElement extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "health_facility_id")
    private HealthFacility healthFacility;
    @ManyToOne
    @JoinColumn(name = "building_element_id")
    private BuildingElement buildingElement;
    @ManyToOne
    @JoinColumn(name = "building_material_id")
    private BuildingMaterial buildingMaterial;

}

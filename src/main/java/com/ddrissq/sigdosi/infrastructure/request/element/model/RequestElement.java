package com.ddrissq.sigdosi.infrastructure.request.element.model;

import com.ddrissq.sigdosi.common.persistence.model.BaseEntity;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.model.BuildingElement;
import com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.model.BuildingMaterial;
import com.ddrissq.sigdosi.infrastructure.request.model.Request;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RequestElement extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private Request request;
    @ManyToOne
    @JoinColumn(name = "building_element_id")
    private BuildingElement buildingElement;
    @ManyToOne
    @JoinColumn(name = "building_material_id")
    private BuildingMaterial buildingMaterial;
    private RequestElementRequiredAction requiredAction;

}

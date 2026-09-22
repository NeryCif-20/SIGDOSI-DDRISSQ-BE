package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.model;

import com.ddrissq.sigdosi.common.persistence.model.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BuildingElement extends BaseEntity {

    private String name;
    private Boolean hasBuildingMaterial;

}

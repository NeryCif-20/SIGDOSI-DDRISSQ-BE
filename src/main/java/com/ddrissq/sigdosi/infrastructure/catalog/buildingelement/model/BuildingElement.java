package com.ddrissq.sigdosi.infrastructure.catalog.buildingelement.model;

import com.ddrissq.sigdosi.common.model.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BuildingElement extends AbstractEntity {

    private String name;
    private Boolean hasBuildingMaterial;

}

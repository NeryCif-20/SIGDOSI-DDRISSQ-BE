package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.model;

import com.ddrissq.sigdosi.common.model.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BuildingMaterial extends AbstractEntity {

    private String code;
    private String name;

}

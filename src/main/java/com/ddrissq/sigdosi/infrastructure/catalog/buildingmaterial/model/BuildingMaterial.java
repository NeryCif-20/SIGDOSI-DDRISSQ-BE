package com.ddrissq.sigdosi.infrastructure.catalog.buildingmaterial.model;

import com.ddrissq.sigdosi.common.persistence.model.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BuildingMaterial extends BaseEntity {

    private String code;
    private String name;

}

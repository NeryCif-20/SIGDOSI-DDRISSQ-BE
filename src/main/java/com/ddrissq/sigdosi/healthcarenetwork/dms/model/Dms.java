package com.ddrissq.sigdosi.healthcarenetwork.dms.model;

import com.ddrissq.sigdosi.common.model.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Dms extends AbstractEntity {

    private String name;

}

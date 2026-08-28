package com.ddrissq.sigdosi.healthcarenetwork.riss.model;

import com.ddrissq.sigdosi.common.model.AbstractEntity;
import com.ddrissq.sigdosi.healthcarenetwork.dms.model.Dms;
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
public class Riss extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "dms_id")
    private Dms dms;
    private String name;

}

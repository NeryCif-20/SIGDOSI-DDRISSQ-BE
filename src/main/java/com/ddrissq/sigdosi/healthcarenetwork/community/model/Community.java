package com.ddrissq.sigdosi.healthcarenetwork.community.model;

import com.ddrissq.sigdosi.common.model.AbstractEntity;
import com.ddrissq.sigdosi.healthcarenetwork.riss.model.Riss;
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
public class Community extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "riss_id")
    private Riss riss;
    private String name;
    private Integer territory;
    private String sector;
    private Long population;

}

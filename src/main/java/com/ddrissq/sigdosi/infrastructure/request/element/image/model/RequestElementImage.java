package com.ddrissq.sigdosi.infrastructure.request.element.image.model;

import com.ddrissq.sigdosi.common.persistence.model.BaseEntity;
import com.ddrissq.sigdosi.infrastructure.request.element.model.RequestElement;
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
public class RequestElementImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_element_id")
    private RequestElement requestElement;
    private String path;

}

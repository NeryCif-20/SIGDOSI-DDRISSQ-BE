package com.ddrissq.sigdosi.infrastructure.request.model;

import com.ddrissq.sigdosi.common.persistence.model.BaseEntity;
import com.ddrissq.sigdosi.healthcarenetwork.healthfacility.model.HealthFacility;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Request extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "health_facility_id")
    private HealthFacility healthFacility;
    private String referenceCode;
    private String requesterName;
    private String requesterEmail;
    private String requesterPhoneNumber;
    private String requesterOfficialPosition;
    private String requesterFunctionalPosition;
    private Short roomsToExpand;
    private BigDecimal aproximateCost;
    private String notes;
    private RequestStatus status;
    private LocalDate requestedDate;

}

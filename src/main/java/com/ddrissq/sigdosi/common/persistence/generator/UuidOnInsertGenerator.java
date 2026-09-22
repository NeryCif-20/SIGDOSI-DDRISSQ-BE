package com.ddrissq.sigdosi.common.persistence.generator;

import com.ddrissq.sigdosi.common.util.UuidGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;
import org.hibernate.generator.EventTypeSets;

import java.util.EnumSet;
import java.util.UUID;

public class UuidOnInsertGenerator implements BeforeExecutionGenerator {

    @Override
    public UUID generate(
            SharedSessionContractImplementor session,
            Object owner,
            Object currentValue,
            EventType eventType) {
        return UuidGenerator.generateV7();
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EventTypeSets.INSERT_ONLY;
    }

}

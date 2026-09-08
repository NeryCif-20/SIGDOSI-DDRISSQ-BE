package com.ddrissq.sigdosi.common.service.util;

import com.ddrissq.sigdosi.common.model.AbstractEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatchHelper {

    public static <T> T resolveValue(T requestValue, T currentValue) {
        return requestValue == null ? currentValue : requestValue;
    }

    public static <T extends AbstractEntity> T resolveEntity(
            UUID entityId,
            T currentEntity,
            Function<UUID, T> finder) {
        if (entityId == null || entityId.equals(currentEntity.getId())) {
            return currentEntity;
        }
        return finder.apply(entityId);
    }

}

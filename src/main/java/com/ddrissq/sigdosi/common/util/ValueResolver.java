package com.ddrissq.sigdosi.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValueResolver {

    public static <V> V resolve(V requested, V current) {
        return requested == null ? current : requested;
    }

    public static <K, V> V resolveByKey(
            K requestedKey,
            V current,
            Function<V, K> keyExtractor,
            Function<K, V> resolver) {
        if (requestedKey == null || (current != null && requestedKey.equals(keyExtractor.apply(current)))) {
            return current;
        }
        return resolver.apply(requestedKey);
    }

}

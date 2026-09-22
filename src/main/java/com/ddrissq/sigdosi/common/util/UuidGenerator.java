package com.ddrissq.sigdosi.common.util;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UuidGenerator {

    private static final TimeBasedEpochGenerator V7_GENERATOR = Generators.timeBasedEpochGenerator();


    public static UUID generateV7() {
        return V7_GENERATOR.generate();
    }

}

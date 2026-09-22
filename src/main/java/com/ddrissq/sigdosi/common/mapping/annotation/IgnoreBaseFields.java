package com.ddrissq.sigdosi.common.mapping.annotation;

import org.mapstruct.Mapping;

import java.lang.annotation.*;

@Mapping(target = "id", ignore = true)
@Mapping(target = "createdAt", ignore = true)
@Mapping(target = "updatedAt", ignore = true)
@Documented
@Retention(value = RetentionPolicy.CLASS)
@Target(value = ElementType.METHOD)
public @interface IgnoreBaseFields {
}

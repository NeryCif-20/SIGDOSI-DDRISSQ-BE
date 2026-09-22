package com.ddrissq.sigdosi.common.persistence.annotation;

import com.ddrissq.sigdosi.common.persistence.generator.UuidOnInsertGenerator;
import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.*;

@IdGeneratorType(value = UuidOnInsertGenerator.class)
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
public @interface GeneratedUuid {
}

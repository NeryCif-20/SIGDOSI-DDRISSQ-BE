package com.ddrissq.sigdosi.common.validation.file.annotation;

import com.ddrissq.sigdosi.common.validation.file.constant.FileErrorMessageKeys;
import com.ddrissq.sigdosi.common.validation.file.validator.FileMetadataValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FileMetadataValidator.class)
public @interface FileMetadata {

    String message() default FileErrorMessageKeys.FILE_METADATA_INVALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

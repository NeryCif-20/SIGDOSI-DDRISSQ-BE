package com.ddrissq.sigdosi.common.validation.validator;

import com.ddrissq.sigdosi.common.validation.annotation.NullableNotBlank;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NullableNotBlankValidator implements ConstraintValidator<NullableNotBlank, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !value.toString().trim().isEmpty();
    }

}

package com.algaworks.algafood.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultipleValidator implements ConstraintValidator<Multiple, Number> {
    private int multipleNumber;

    @Override
    public void initialize(Multiple constraintAnnotation) {
        this.multipleNumber = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean valid = true;

        if (value != null) {
            var decimalValue = BigDecimal.valueOf(value.doubleValue());
            var decimalMultiple = BigDecimal.valueOf(this.multipleNumber);
            var remainder = decimalValue.remainder(decimalMultiple);

            valid = BigDecimal.ZERO.compareTo(remainder) == 0;
        }

        return valid;
    }

}

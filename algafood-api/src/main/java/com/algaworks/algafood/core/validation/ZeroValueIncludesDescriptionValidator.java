package com.algaworks.algafood.core.validation;

import org.springframework.beans.BeanUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import java.math.BigDecimal;

public class ZeroValueIncludesDescriptionValidator implements ConstraintValidator<ZeroValueIncludesDescription, Object> {
    private String fieldValue;
    private String fieldDescription;
    private String mandatoryDescription;

    @Override
    public void initialize(ZeroValueIncludesDescription constraint) {
        this.fieldValue = constraint.fieldValue();
        this.fieldDescription = constraint.fieldDescription();
        this.mandatoryDescription = constraint.mandatoryDescription();
    }

    @Override
    public boolean isValid(Object validationObject, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(validationObject.getClass(), fieldValue)
                    .getReadMethod().invoke(validationObject);

            String description = (String) BeanUtils.getPropertyDescriptor(validationObject.getClass(), fieldDescription)
                    .getReadMethod().invoke(validationObject);

            if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
                valid = description.toLowerCase().contains(this.mandatoryDescription.toLowerCase());
            }

            return valid;
        } catch (Exception e) {
            throw new ValidationException();
        }
    }

}

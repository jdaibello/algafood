package com.algaworks.algafood.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ZeroValueIncludesDescriptionValidator.class})
public @interface ZeroValueIncludesDescription {
    String message() default "Descrição obrigatória inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldValue();

    String fieldDescription();

    String mandatoryDescription();
}

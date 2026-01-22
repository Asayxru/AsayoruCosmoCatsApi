package com.kpi.cosmocats.cosmocatsapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = CosmicWordCheckValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CosmicWordCheck {

    String message() default "Name must contain something cosmiiiic";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package com.kpi.cosmocats.cosmocatsapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CosmicWordCheckValidator
        implements ConstraintValidator<CosmicWordCheck, String> {

    private static final List<String> WORDS =
            List.of("star", "galaxy", "comet");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return WORDS.stream().anyMatch(value.toLowerCase()::contains);
    }
}

//  |\__/,|   (`\
//_.|o o  |_   ) )
//-(((---(((--------
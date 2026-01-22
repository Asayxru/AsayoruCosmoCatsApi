package com.kpi.cosmocats.cosmocatsapi.feature;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FeatureToggle {
    String value();
}

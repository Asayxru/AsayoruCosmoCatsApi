package com.kpi.cosmocats.cosmocatsapi.feature;

public interface FeatureToggleService {
    boolean isEnabled(String featureName);
}

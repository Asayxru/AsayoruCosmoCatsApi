package com.kpi.cosmocats.cosmocatsapi.feature;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FeatureToggleServiceRealisation implements FeatureToggleService {

    private final Map<String, Boolean> featureFlags;

    public FeatureToggleServiceRealisation(Map<String, Boolean> featureFlags) {
        this.featureFlags = featureFlags;
    }

    @Override
    public boolean isEnabled(String featureName) {
        return featureFlags.getOrDefault(featureName, false);
    }
}

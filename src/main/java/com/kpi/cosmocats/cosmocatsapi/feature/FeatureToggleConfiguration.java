package com.kpi.cosmocats.cosmocatsapi.feature;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FeatureToggleConfiguration {

    @Value("${feature.cosmoCats.enabled:false}")
    private boolean cosmoCatsEnabled;

    @Value("${feature.kittyProducts.enabled:false}")
    private boolean kittyProductsEnabled;

    @Bean
    public Map<String, Boolean> featureFlags() {
        Map<String, Boolean> flags = new HashMap<>();
        flags.put("cosmoCats", cosmoCatsEnabled);
        flags.put("kittyProducts", kittyProductsEnabled);
        return flags;
    }
}

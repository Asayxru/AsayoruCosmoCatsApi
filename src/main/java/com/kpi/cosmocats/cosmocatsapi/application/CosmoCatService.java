package com.kpi.cosmocats.cosmocatsapi.application;

import com.kpi.cosmocats.cosmocatsapi.feature.FeatureToggle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CosmoCatService {

    @FeatureToggle("cosmoCats")
    public List<String> getCosmoCats() {
        return List.of("1", "2", "3");
    }
}

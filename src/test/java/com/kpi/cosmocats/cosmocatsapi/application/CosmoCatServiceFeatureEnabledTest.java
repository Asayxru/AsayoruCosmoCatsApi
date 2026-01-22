package com.kpi.cosmocats.cosmocatsapi.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(properties = "feature.cosmoCats.enabled=true")
class CosmoCatServiceFeatureEnabledTest {

    @Autowired
    private CosmoCatService cosmoCatService;

    @Test
    void getCosmoCats_returnsCats_whenFeatureEnabled() {
        List<String> cats = cosmoCatService.getCosmoCats();
        assertFalse(cats.isEmpty());
    }
}

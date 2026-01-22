package com.kpi.cosmocats.cosmocatsapi.application;

import com.kpi.cosmocats.cosmocatsapi.error.FeatureNotAvailableException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "feature.cosmoCats.enabled=false")
class CosmoCatServiceFeatureDisabledTest {

    @Autowired
    private CosmoCatService cosmoCatService;

    @Test
    void getCosmoCats_throwsException_whenFeatureDisabled() {
        assertThrows(FeatureNotAvailableException.class, () -> cosmoCatService.getCosmoCats());
    }
}

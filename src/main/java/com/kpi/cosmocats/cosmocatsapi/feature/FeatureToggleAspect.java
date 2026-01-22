package com.kpi.cosmocats.cosmocatsapi.feature;

import com.kpi.cosmocats.cosmocatsapi.error.FeatureNotAvailableException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    public FeatureToggleAspect(FeatureToggleService featureToggleService) {
        this.featureToggleService = featureToggleService;
    }

    @Around("@annotation(featureToggle)")
    public Object around(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        String featureName = featureToggle.value();

        if (featureToggleService.isEnabled(featureName)) {
            return joinPoint.proceed();
        }

        throw new FeatureNotAvailableException("Feature '" + featureName + "' is disabled");
    }
}

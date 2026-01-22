package com.kpi.cosmocats.cosmocatsapi.error;

public class FeatureNotAvailableException extends RuntimeException {
    public FeatureNotAvailableException(String message) {
        super(message);
    }
}

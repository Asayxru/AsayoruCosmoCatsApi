package com.kpi.cosmocats.cosmocatsapi.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}

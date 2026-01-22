//testclient
package com.kpi.cosmocats.cosmocatsapi.infrastructure.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class CategoryClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean categoryExists(UUID categoryId) {
        String url = "http://localhost:8089/categories/" + categoryId;
        Boolean response = restTemplate.getForObject(url, Boolean.class);
        return Boolean.TRUE.equals(response);
    }
}

//testclient
package com.kpi.cosmocats.cosmocatsapi.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class CategoryClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public CategoryClient(RestTemplate restTemplate,
                          @Value("${clients.category.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public boolean categoryExists(UUID categoryId) {
        String url = baseUrl + "/categories/" + categoryId;
        try {
            Boolean response = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(response);
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}
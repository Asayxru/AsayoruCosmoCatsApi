package com.kpi.cosmocats.cosmocatsapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.cosmocats.cosmocatsapi.application.ProductService;
import com.kpi.cosmocats.cosmocatsapi.dto.ProductCreateRequest;
import com.kpi.cosmocats.cosmocatsapi.dto.ProductResponse;
import com.kpi.cosmocats.cosmocatsapi.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_whenValidRequest_returns200() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest();
        request.setName("star milk");
        request.setDescription("desc");
        request.setPrice(BigDecimal.valueOf(10));
        request.setCategoryId(UUID.randomUUID());

        ProductResponse response = new ProductResponse();
        UUID id = UUID.randomUUID();
        response.setId(id);
        response.setName(request.getName());
        response.setDescription(request.getDescription());
        response.setPrice(request.getPrice());
        response.setCategoryId(request.getCategoryId());

        Mockito.when(service.create(any(ProductCreateRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("star milk"))
                .andExpect(jsonPath("$.price").value(10.0));

        Mockito.verify(service).create(any(ProductCreateRequest.class));
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void create_whenInvalidRequest_returns400() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest();
        request.setName("name");
        request.setDescription("desc");
        request.setPrice(BigDecimal.valueOf(10));
        request.setCategoryId(UUID.randomUUID());

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(service);
    }

    @Test
    void getById_whenNotFound_returns404() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(service.getById(eq(id)))
                .thenThrow(new NotFoundException("Product not found"));

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isNotFound());

        Mockito.verify(service).getById(id);
        Mockito.verifyNoMoreInteractions(service);
    }
}

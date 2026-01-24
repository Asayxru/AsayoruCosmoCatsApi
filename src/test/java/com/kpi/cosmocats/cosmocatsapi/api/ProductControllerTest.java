package com.kpi.cosmocats.cosmocatsapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.cosmocats.cosmocatsapi.infrastructure.client.CategoryClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.liquibase.enabled=false",
        "spring.autoconfigure.exclude=" +
                "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
                "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryClient categoryClient;

    @Test
    void create_whenValidRequest_returns200_andProductJson() throws Exception {
        when(categoryClient.categoryExists(any())).thenReturn(true);

        var request = new java.util.HashMap<String, Object>();
        request.put("name", "star milk");
        request.put("description", "desc");
        request.put("price", BigDecimal.valueOf(10));
        request.put("categoryId", UUID.randomUUID());

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("star milk"))
                .andExpect(jsonPath("$.price").value(10));
    }

    @Test
    void create_whenInvalidRequest_returns400_withProblemJson() throws Exception {
        var request = new java.util.HashMap<String, Object>();
        request.put("name", "name"); // fails @CosmicWordCheck
        request.put("description", "desc");
        request.put("price", BigDecimal.valueOf(10));
        request.put("categoryId", UUID.randomUUID());

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Name must contain something cosmiic"))
                .andExpect(jsonPath("$.path").value("/api/products"));
    }

    @Test
    void getById_whenNotFound_returns404_withProblemJson() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.path").value("/api/products/" + id));
    }
}

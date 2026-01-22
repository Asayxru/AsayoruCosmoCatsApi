package com.kpi.cosmocats.cosmocatsapi.dto;

import com.kpi.cosmocats.cosmocatsapi.validation.CosmicWordCheck;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductCreateRequest {

    @NotBlank
    @Size(min = 3, max = 100)
    @CosmicWordCheck
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private UUID categoryId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public UUID getCategoryId() { return categoryId; }
    public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }
}

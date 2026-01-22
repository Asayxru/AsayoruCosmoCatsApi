package com.kpi.cosmocats.cosmocatsapi.api;

import com.kpi.cosmocats.cosmocatsapi.application.ProductService;
import com.kpi.cosmocats.cosmocatsapi.dto.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ProductResponse create(@Valid @RequestBody ProductCreateRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody ProductUpdateRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
//  |\__/,|   (`\
//_.|o o  |_   ) )
//-(((---(((--------
package com.kpi.cosmocats.cosmocatsapi.application;

import com.kpi.cosmocats.cosmocatsapi.domain.model.Product;
import com.kpi.cosmocats.cosmocatsapi.domain.repository.ProductRepository;
import com.kpi.cosmocats.cosmocatsapi.dto.*;
import com.kpi.cosmocats.cosmocatsapi.error.NotFoundException;
import com.kpi.cosmocats.cosmocatsapi.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ProductResponse create(ProductCreateRequest request) {
        Product product = mapper.toDomain(request);
        product.setId(UUID.randomUUID());
        repository.save(product);
        return mapper.toResponse(product);
    }

    public List<ProductResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ProductResponse getById(UUID id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return mapper.toResponse(product);
    }

    public ProductResponse update(UUID id, ProductUpdateRequest request) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        mapper.update(request, product);
        return mapper.toResponse(repository.save(product));
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Product not found");
        }
        repository.deleteById(id);
    }
}

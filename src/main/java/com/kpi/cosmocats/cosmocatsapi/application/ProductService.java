package com.kpi.cosmocats.cosmocatsapi.application;

import com.kpi.cosmocats.cosmocatsapi.domain.model.Product;
import com.kpi.cosmocats.cosmocatsapi.domain.repository.ProductRepository;
import com.kpi.cosmocats.cosmocatsapi.dto.ProductCreateRequest;
import com.kpi.cosmocats.cosmocatsapi.dto.ProductResponse;
import com.kpi.cosmocats.cosmocatsapi.dto.ProductUpdateRequest;
import com.kpi.cosmocats.cosmocatsapi.error.NotFoundException;
import com.kpi.cosmocats.cosmocatsapi.infrastructure.client.CategoryClient;
import com.kpi.cosmocats.cosmocatsapi.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final CategoryClient categoryClient;

    public ProductService(ProductRepository repository, ProductMapper mapper, CategoryClient categoryClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.categoryClient = categoryClient;
    }

    public ProductResponse create(ProductCreateRequest request) {

        if (!categoryClient.categoryExists(request.getCategoryId())) {
            throw new NotFoundException("Category not found");
        }

        Product product = mapper.toDomain(request);
        product.setId(UUID.randomUUID());
        repository.save(product);
        return mapper.toResponse(product);
    }

    public List<ProductResponse> getAll() {
        return repository.findAll()
                .stream()
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

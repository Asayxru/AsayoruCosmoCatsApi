package com.kpi.cosmocats.cosmocatsapi.infrastructure.repository;

import com.kpi.cosmocats.cosmocatsapi.domain.model.Product;
import com.kpi.cosmocats.cosmocatsapi.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<UUID, Product> storage = new ConcurrentHashMap<>();

    public Product save(Product product) {
        storage.put(product.getId(), product);
        return product;
    }

    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Product> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void deleteById(UUID id) {
        storage.remove(id);
    }

    public boolean existsById(UUID id) {
        return storage.containsKey(id);
    }
}

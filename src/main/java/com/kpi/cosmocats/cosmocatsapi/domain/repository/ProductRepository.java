package com.kpi.cosmocats.cosmocatsapi.domain.repository;

import com.kpi.cosmocats.cosmocatsapi.domain.model.Product;

import java.util.*;

public interface ProductRepository {

    Product save(Product product);
    Optional<Product> findById(UUID id);
    List<Product> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
}

package com.kpi.cosmocats.cosmocatsapi.mapper;

import com.kpi.cosmocats.cosmocatsapi.domain.model.Product;
import com.kpi.cosmocats.cosmocatsapi.dto.ProductCreateRequest;
import com.kpi.cosmocats.cosmocatsapi.dto.ProductResponse;
import com.kpi.cosmocats.cosmocatsapi.dto.ProductUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toDomain(ProductCreateRequest request);

    ProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    void update(ProductUpdateRequest request, @MappingTarget Product product);
}

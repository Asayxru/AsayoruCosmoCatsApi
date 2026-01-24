package com.kpi.cosmocats.cosmocatsapi.application;

import com.kpi.cosmocats.cosmocatsapi.infrastructure.client.CategoryClient;
import com.kpi.cosmocats.cosmocatsapi.domain.model.Product;
import com.kpi.cosmocats.cosmocatsapi.domain.repository.ProductRepository;
import com.kpi.cosmocats.cosmocatsapi.dto.ProductCreateRequest;
import com.kpi.cosmocats.cosmocatsapi.dto.ProductResponse;
import com.kpi.cosmocats.cosmocatsapi.dto.ProductUpdateRequest;
import com.kpi.cosmocats.cosmocatsapi.error.NotFoundException;
import com.kpi.cosmocats.cosmocatsapi.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @Mock
    private CategoryClient categoryClient;

    @InjectMocks
    private ProductService service;

    @Test
    void create_generatesId_savesAndReturnsResponse() {
        ProductCreateRequest request = new ProductCreateRequest();
        request.setName("smthng");
        request.setPrice(BigDecimal.valueOf(10));
        request.setCategoryId(UUID.randomUUID());

        Product product = new Product();
        ProductResponse response = new ProductResponse();

        when(categoryClient.categoryExists(any())).thenReturn(true);
        when(mapper.toDomain(request)).thenReturn(product);
        when(repository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));
        when(mapper.toResponse(any(Product.class))).thenReturn(response);

        ProductResponse result = service.create(request);

        assertNotNull(result);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(repository).save(captor.capture());

        Product saved = captor.getValue();
        assertNotNull(saved.getId(), "ID має генеруватися в сервісі");

        verify(categoryClient).categoryExists(any());
        verify(mapper).toDomain(request);
        verify(mapper).toResponse(product);
        verifyNoMoreInteractions(repository, mapper, categoryClient);
    }

    @Test
    void getAll_returnsMappedList() {
        Product p1 = new Product();
        Product p2 = new Product();

        when(repository.findAll()).thenReturn(List.of(p1, p2));
        when(mapper.toResponse(p1)).thenReturn(new ProductResponse());
        when(mapper.toResponse(p2)).thenReturn(new ProductResponse());

        List<ProductResponse> result = service.getAll();

        assertEquals(2, result.size());

        verify(repository).findAll();
        verify(mapper).toResponse(p1);
        verify(mapper).toResponse(p2);
        verifyNoMoreInteractions(repository, mapper, categoryClient);
    }

    @Test
    void getById_whenExists_returnsResponse() {
        UUID id = UUID.randomUUID();
        Product product = new Product();
        ProductResponse response = new ProductResponse();

        when(repository.findById(id)).thenReturn(Optional.of(product));
        when(mapper.toResponse(product)).thenReturn(response);

        ProductResponse result = service.getById(id);

        assertNotNull(result);

        verify(repository).findById(id);
        verify(mapper).toResponse(product);
        verifyNoMoreInteractions(repository, mapper, categoryClient);
    }

    @Test
    void getById_whenNotExists_throwsNotFound() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(id));

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository, mapper, categoryClient);
    }

    @Test
    void update_whenExists_updatesAndReturnsResponse() {
        UUID id = UUID.randomUUID();
        ProductUpdateRequest request = new ProductUpdateRequest();
        Product product = new Product();
        Product saved = new Product();
        ProductResponse response = new ProductResponse();

        when(repository.findById(id)).thenReturn(Optional.of(product));
        when(repository.save(product)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        ProductResponse result = service.update(id, request);

        assertNotNull(result);

        verify(repository).findById(id);
        verify(mapper).update(request, product);
        verify(repository).save(product);
        verify(mapper).toResponse(saved);
        verifyNoMoreInteractions(repository, mapper, categoryClient);
    }

    @Test
    void update_whenNotExists_throwsNotFound() {
        UUID id = UUID.randomUUID();
        ProductUpdateRequest request = new ProductUpdateRequest();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.update(id, request));

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository, mapper, categoryClient);
    }

    @Test
    void delete_whenExists_deletes() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repository).existsById(id);
        verify(repository).deleteById(id);
        verifyNoMoreInteractions(repository, mapper, categoryClient);
    }

    @Test
    void delete_whenNotExists_throwsNotFound() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.delete(id));

        verify(repository).existsById(id);
        verifyNoMoreInteractions(repository, mapper, categoryClient);
    }
}

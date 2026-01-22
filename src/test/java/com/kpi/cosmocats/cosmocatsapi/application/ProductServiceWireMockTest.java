package com.kpi.cosmocats.cosmocatsapi.application;


import com.kpi.cosmocats.cosmocatsapi.domain.model.Product;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.kpi.cosmocats.cosmocatsapi.domain.repository.ProductRepository;
import com.kpi.cosmocats.cosmocatsapi.dto.ProductCreateRequest;
import com.kpi.cosmocats.cosmocatsapi.error.NotFoundException;
import com.kpi.cosmocats.cosmocatsapi.infrastructure.client.CategoryClient;
import com.kpi.cosmocats.cosmocatsapi.mapper.ProductMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;


import static org.mockito.Mockito.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WireMockTest(httpPort = 8089)
class ProductServiceWireMockTest {

    @Test
    void create_whenCategoryDoesNotExist_throwsException() {
        UUID categoryId = UUID.randomUUID();

        stubFor(get(urlEqualTo("/categories/" + categoryId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("false")));

        ProductRepository repository = mock(ProductRepository.class);
        ProductMapper mapper = mock(ProductMapper.class);
        CategoryClient categoryClient = new CategoryClient();

        Product product = new Product();
        when(mapper.toDomain(any())).thenReturn(product);

        ProductService service =
                new ProductService(repository, mapper, categoryClient);

        ProductCreateRequest request = new ProductCreateRequest();
        request.setName("smthng");
        request.setPrice(BigDecimal.valueOf(10));
        request.setCategoryId(categoryId);

        assertThrows(NotFoundException.class,
                () -> service.create(request));
    }
}

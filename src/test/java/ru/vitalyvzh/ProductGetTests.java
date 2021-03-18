package ru.vitalyvzh;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.vitalyvzh.base.enums.Errors;
import ru.vitalyvzh.db.dao.ProductsMapper;
import ru.vitalyvzh.dto.Product;
import ru.vitalyvzh.service.ProductService;
import ru.vitalyvzh.utils.*;


import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Проверки GET запросов товаров")
public class ProductGetTests {

    Faker faker = new Faker();
    static ProductService productService;
    Integer productId;
    static ProductsMapper productsMapper;

    @BeforeAll
    static void beforeAll() throws IOException {

        productsMapper = DbUtils
                .getProductsMapper();
        productService = RetrofitUtils
                .getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() throws IOException {

        productId = CreateProduct.createProduct().getId();
    }

    @DisplayName("Запрос продукта по заданному ID (позитивный тест)")
    @Test
    void getProductPositiveTest() throws IOException {
        Response<Product> response = productService
                .getProduct(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body().getId()).isNotNull();
        assertThat((response.body().getId()).equals(productId));

    }

    @DisplayName("Запрос продукта по несуществующему ID (негативный тест)")
    @Test
    void getProductNegativeTest() throws IOException {
        Response<Product> response = productService
                .getProduct(faker.number().numberBetween(1, 1000))
                .execute();
        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.code()).isEqualTo(404);
        assertThat(response.errorBody().string()).contains(Errors.CODE404PROD.getMessage());
    }

    @DisplayName("Запрос продукта без указания ID (негативный тест)")
    @Test
    void getAllProductPositiveTest() throws IOException {
        Response<Product[]> response = productService
                .getProducts()
                .execute();
        assertThat(response.code()).isEqualTo(500);
        assertThat(response.isSuccessful()).isFalse();
    }

    @AfterEach
    void tearDown() throws IOException {

        DeleteProduct.finishTests(productId);
    }
}
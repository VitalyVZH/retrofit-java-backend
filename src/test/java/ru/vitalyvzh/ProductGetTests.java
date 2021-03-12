package ru.vitalyvzh;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.vitalyvzh.base.enums.Errors;
import ru.vitalyvzh.db.dao.ProductsMapper;
import ru.vitalyvzh.dto.Product;
import ru.vitalyvzh.service.ProductService;
import ru.vitalyvzh.utils.DbUtils;
import ru.vitalyvzh.utils.RetrofitUtils;
import ru.vitalyvzh.utils.SetUp;
import ru.vitalyvzh.utils.TearDown;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Проверки запросов конкретных товаров")
public class ProductGetTests {

    Faker faker = new Faker();
    static ProductService productService;
    Integer productId;
    Response<Product> response;
    static ProductsMapper productsMapper;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {

        productsMapper = DbUtils
                .getProductsMapper();
        productService = RetrofitUtils
                .getRetrofit()
                .create(ProductService.class);
    }

    @SneakyThrows
    @BeforeEach
    void setUp() {

        response = productService
                .createProduct(SetUp.createProduct())
                .execute();

        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.code()).isEqualTo(201);
    }

    @DisplayName("Запрос существующего продукта")
    @SneakyThrows
    @Test
    void getProductPositiveTest() {
        Response<Product> response = productService
                .getProduct(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body().getId()).isNotNull();
        assertThat((response.body().getId()).equals(productId));

    }

    @DisplayName("Запрос продукта по несуществующему ID")
    @SneakyThrows
    @Test
    void getProductNegativeTest() {
        Response<Product> response = productService
                .getProduct(faker.number().numberBetween(1, 1000))
                .execute();
        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.code()).isEqualTo(404);
        assertThat(response.errorBody().string()).contains(Errors.CODE404PROD.getMessage());
    }

    @DisplayName("Запрос всех продуктов без указания ID")
    @SneakyThrows
    @Test
    void getAllProductPositiveTest() {
        Response<Product[]> response = productService
                .getProducts()
                .execute();
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.isSuccessful()).isTrue();
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {

        TearDown.finishTests(productId, productService);
    }
}
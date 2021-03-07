package ru.vitalyvzh;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.vitalyvzh.base.enums.Errors;
import ru.vitalyvzh.dto.Product;
import ru.vitalyvzh.service.ProductService;
import ru.vitalyvzh.utils.RetrofitUtils;
import ru.vitalyvzh.utils.SetUp;
import ru.vitalyvzh.utils.TearDown;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Проверки обновления товаров")
public class ProductUpdateTests {

    Faker faker = new Faker();
    static ProductService productService;
    Product product;
    Product secondProduct;
    Integer productId;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils
                .getRetrofit()
                .create(ProductService.class);
    }

    @SneakyThrows
    @BeforeEach
    void setUp() {

        product = SetUp.createProduct();
        secondProduct = SetUp.createProduct();

        retrofit2.Response<Product> response = productService
                .createProduct(product)
                .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.code()).isEqualTo(201);
    }

    @DisplayName("Обновление существующего продукта без указания ID")
    @SneakyThrows
    @Test
    void updateProductNewPositiveTest() {

        Response<ResponseBody> response = productService
                .updateProduct(product.withTitle(faker.food().fruit()))
                .execute();

        assertThat(response.errorBody().string().contains(Errors.CODE400NOTNULL.getMessage()));

    }

    @DisplayName("Обновление продукта по несуществующему ID")
    @SneakyThrows
    @Test
    void updateProductNoExistIdNegativeTest() {
        Response<ResponseBody> response = productService
                .updateProduct(faker.number().numberBetween(1, 1000), secondProduct)
                .execute();
        assertThat(response.code()).isEqualTo(405);
        assertThat(response.errorBody().string()).contains(Errors.CODE405.getMessage());
    }

    @DisplayName("Обновление продукта по существующему ID")
    @SneakyThrows
    @Test
    void updateProductExistIdNegativeTest() {
        Response<ResponseBody> response = productService
                .updateProduct(productId, secondProduct)
                .execute();
        assertThat(response.code()).isEqualTo(405);
        assertThat(response.errorBody().string()).contains(Errors.CODE405.getMessage());
    }

    @AfterEach
    void tearDown() {

        TearDown.finishTests(productId, productService);
    }
}

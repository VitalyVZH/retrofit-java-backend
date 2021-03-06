package ru.vitalyvzh;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.vitalyvzh.base.enums.CategoryType;
import ru.vitalyvzh.base.enums.Errors;
import ru.vitalyvzh.dto.Product;
import ru.vitalyvzh.service.ProductService;
import ru.vitalyvzh.utils.RetrofitUtils;
import ru.vitalyvzh.utils.TearDown;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Проверки запросов конкретных товаров")
public class ProductGetTests {

    Faker faker = new Faker();
    static ProductService productService;
    Product product;
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
        product = new Product()
                .withTitle(faker.food().fruit())
                .withPrice((int) (Math.random() * 1000 + 1))
                .withCategoryTitle(CategoryType.FOOD.getTitle());

        retrofit2.Response<Product> response = productService
                .createProduct(product)
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

    }

    @DisplayName("Запрос продукта по несуществующему ID")
    @SneakyThrows
    @Test
    void getProductNegativeTest() {
        Response<Product> response = productService
                .getProduct(faker.number().numberBetween(1, 1000))
                .execute();
        assertThat(response.code()).isEqualTo(404);
        assertThat(response.errorBody().string()).contains(Errors.CODE404PROD.getMessage());
    }

    @DisplayName("Запрос всех продуктов без указания ID")
    @SneakyThrows
    @Test
    void getAllProductPositiveTest() {
        Response<Product> response = productService
                .getProduct()
                .execute();
//        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.code()).isEqualTo(200);
//        assertThat(response.body().getId()).isNotNull();
    }


    @AfterEach
    void tearDown() {

        TearDown.finishTests(productId, productService);
    }
}

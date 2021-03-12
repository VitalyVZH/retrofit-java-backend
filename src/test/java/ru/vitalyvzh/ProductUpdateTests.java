package ru.vitalyvzh;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.vitalyvzh.base.enums.CategoryType;
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
    Response<Product> response;

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

        response = productService
                .createProduct(SetUp.createProduct())
                .execute();

        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.code()).isEqualTo(201);
    }

    @DisplayName("Позитивное обновление существующего продукта")
    @SneakyThrows
    @Test
    void updateProductNewPositiveTest() {

        Response<Product> response = productService
                .updateProduct(new Product()
                .withId(productId)
                .withTitle(faker.food().fruit())
                .withPrice(faker.number().numberBetween(1, 1000))
                .withCategoryTitle(CategoryType.FOOD.getTitle()))
                .execute();

        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.code()).isEqualTo(200);

    }

    @DisplayName("Обновление существующего продукта по несуществующему ID")
    @SneakyThrows
    @Test
    void updateProductNoExistIdNegativeTest() {
        Response<Product> response = productService
                .updateProduct(new Product()
                .withId(faker.number().randomDigit())
                .withTitle(faker.food().fruit())
                .withPrice(faker.number().numberBetween(1, 1000))
                .withCategoryTitle(CategoryType.FOOD.getTitle()))
                .execute();
        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.code()).isEqualTo(400);
        assertThat(response.errorBody().string()).contains(Errors.CODE400NOEXIST.getMessage());
    }

    @DisplayName("Обновление существующего продукта с несуществующей категорией")
    @SneakyThrows
    @Test
    void updateProductNoExistCategoryNegativeTest() {

        Response<Product> response = productService
                .updateProduct(new Product()
                .withId(productId)
                .withTitle(faker.food().fruit())
                .withPrice(faker.number().numberBetween(1, 1000))
                .withCategoryTitle(faker.animal().name()))
                .execute();

        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.code()).isEqualTo(500);
        assertThat(response.errorBody().string()).contains(Errors.CODE500.getMessage());

    }

    @SneakyThrows
    @AfterEach
    void tearDown() {

        TearDown.finishTests(productId, productService);
    }
}

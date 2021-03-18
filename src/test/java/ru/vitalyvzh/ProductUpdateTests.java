package ru.vitalyvzh;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.vitalyvzh.base.enums.CategoryType;
import ru.vitalyvzh.base.enums.Errors;
import ru.vitalyvzh.db.dao.ProductsMapper;
import ru.vitalyvzh.dto.Product;
import ru.vitalyvzh.service.ProductService;
import ru.vitalyvzh.utils.*;


import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Проверки PUT запросов товаров")
public class ProductUpdateTests {

    Faker faker = new Faker();
    static ProductService productService;
    static Integer productId;
    static ProductsMapper productsMapper;
    Product product;

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

        product = CreateProduct.createProduct();
        productId = product.getId();
        assertThat(product.getId()).isNotNull();

    }

    @DisplayName("Обновление существующего продукта (позитивный тест)")
    @Test
    void updateProductNewPositiveTest() throws IOException {

        Response<Product> response = productService
                .updateProduct(new Product()
                .withId(productId)
                .withTitle(faker.aviation().aircraft())
                .withPrice(faker.number().numberBetween(10000, 25000))
                .withCategoryTitle(CategoryType.AUTOANDINDUSTRIAL.getTitle()))
                .execute();

        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.code()).isEqualTo(200);

    }

    @DisplayName("Обновление продукта по несуществующему ID (негативный тест)")
    @Test
    void updateProductNoExistIdNegativeTest() throws IOException {
        Response<Product> response = productService
                .updateProduct(new Product()
                .withId(faker.number().randomDigit())
                .withTitle(faker.food().fruit())
                .withPrice(faker.number().numberBetween(1, 1000))
                .withCategoryTitle(CategoryType.FOOD.getTitle()))
                .execute();
        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.code()).isEqualTo(400);
        assertThat(response.errorBody().string()).contains(Errors.CODE400NOT_EXIST.getMessage());
    }

    @DisplayName("Обновление продукта в несуществующей категории (негативный тест)")
    @Test
    void updateProductNoExistCategoryNegativeTest() throws IOException {

        Response<Product> response = productService
                .updateProduct(new Product()
                .withId(productId)
                .withTitle(faker.aviation().aircraft())
                .withPrice(faker.number().numberBetween(1, 1000))
                .withCategoryTitle(faker.animal().name()))
                .execute();

        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.code()).isEqualTo(500);
        assertThat(response.errorBody().string()).contains(Errors.CODE500.getMessage());

    }

    @AfterEach
    void tearDown() throws IOException {

        DeleteProduct.finishTests(productId);
    }
}

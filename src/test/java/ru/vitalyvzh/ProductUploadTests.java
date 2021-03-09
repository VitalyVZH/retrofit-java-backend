package ru.vitalyvzh;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import ru.vitalyvzh.base.enums.CategoryType;
import ru.vitalyvzh.base.enums.Errors;
import ru.vitalyvzh.dto.Product;
import ru.vitalyvzh.service.ProductService;
import ru.vitalyvzh.utils.RetrofitUtils;
import ru.vitalyvzh.utils.SetUp;
import ru.vitalyvzh.utils.TearDown;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Проверки добавления конкретных товаров")
public class ProductUploadTests {

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

    @BeforeEach
    void setUp() {

        product = SetUp.createProduct();

    }

    @DisplayName("Добавление нового товара")
    @SneakyThrows
    @Test
    void createNewProductPositiveTest() {
        retrofit2.Response<Product> response = productService
                .createProduct(product)
                .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.code()).isEqualTo(201);
        assertThat(response.body().getCategoryTitle()).isEqualTo(CategoryType.FOOD.getTitle());
    }

    @DisplayName("Негативный тест добавления нового товара")
    @SneakyThrows
    @Test
    void createNewProductNegativeTest() {
        retrofit2.Response<Product> response = productService
                .createProduct(product.withId(faker.number().numberBetween(1, 1000)))
                .execute();

        if(response.body() != null) {
            productId = response.body().getId();
        } else {
            productId = null;
        }

        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.errorBody().string()).contains(Errors.CODE400NULL.getMessage());
    }

    @AfterEach
    void tearDown() {

        TearDown.finishTests(productId, productService);
    }
}

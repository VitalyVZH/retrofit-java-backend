package ru.vitalyvzh;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import ru.vitalyvzh.base.enums.CategoryType;
import ru.vitalyvzh.dto.Product;
import ru.vitalyvzh.service.ProductService;
import ru.vitalyvzh.utils.RetrofitUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Проверки для конкретных товаров")
public class ProductTests {

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

    @DisplayName("Добавление нового товара")
    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().fruit())
                .withPrice((int) (Math.random() * 1000 + 1))
                .withCategoryTitle(CategoryType.FOOD.getTitle());
    }

    @SneakyThrows
    @Test
    void createNewProductTest() {
        retrofit2.Response<Product> response = productService
                .createProduct(product)
                .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
    }

    @AfterEach
    void tearDown() {
        try {
            retrofit2.Response<ResponseBody> response = productService
                    .deleteProduct(productId)
                    .execute();
            assertThat(response.isSuccessful()).isTrue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

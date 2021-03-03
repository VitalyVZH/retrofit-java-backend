package ru.vitalyvzh;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.vitalyvzh.base.enums.CategoryType;
import ru.vitalyvzh.dto.Category;
import ru.vitalyvzh.dto.Product;
import ru.vitalyvzh.service.ProductService;
import ru.vitalyvzh.utils.RetrofitUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vitalyvzh.base.enums.CategoryType.FOOD;

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
    void getProductTest() {
        Response<Product> response = productService
                .getCategory(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body().getId()).isNotNull();

    }

    @AfterEach
    void tearDown() {

        try {
            retrofit2.Response<ResponseBody> response = productService
                    .deleteProduct(productId)
                    .execute();
            assertThat(response.isSuccessful()).isTrue();
            assertThat(response.code()).isEqualTo(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

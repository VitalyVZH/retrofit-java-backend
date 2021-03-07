package ru.vitalyvzh;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vitalyvzh.base.enums.CategoryType;
import ru.vitalyvzh.base.enums.Errors;
import ru.vitalyvzh.dto.Product;
import ru.vitalyvzh.service.ProductService;
import ru.vitalyvzh.utils.RetrofitUtils;
import ru.vitalyvzh.utils.SetUp;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDeleteTests {

    Faker faker = new Faker();
    static ProductService productService;
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

        retrofit2.Response<Product> response = productService
                .createProduct(SetUp.createProduct())
                .execute();
        productId = response.body().getId();
    }

    @DisplayName("Удаление продукта по заданному ID")
    @SneakyThrows
    @Test
    void deleteProductPositiveTest() {
        retrofit2.Response<ResponseBody> response = productService
                .deleteProduct(productId)
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.code()).isEqualTo(200);
    }

    @DisplayName("Удаление продукта по не существующему ID")
    @SneakyThrows
    @Test
    void deleteProductNegativeTest() {
        retrofit2.Response<ResponseBody> response = productService
                .deleteProduct(faker.number().numberBetween(1, 1000))
                .execute();

        assertThat(response.code()).isEqualTo(500);
        assertThat(response.errorBody().string().contains(Errors.CODE500.getMessage()));
    }
}

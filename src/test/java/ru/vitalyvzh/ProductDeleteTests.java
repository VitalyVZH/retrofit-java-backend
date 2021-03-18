package ru.vitalyvzh;

import com.github.javafaker.Faker;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import ru.vitalyvzh.base.enums.Errors;
import ru.vitalyvzh.db.dao.ProductsMapper;
import ru.vitalyvzh.service.ProductService;
import ru.vitalyvzh.utils.CreateProduct;
import ru.vitalyvzh.utils.DbUtils;
import ru.vitalyvzh.utils.DeleteProduct;
import ru.vitalyvzh.utils.RetrofitUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Проверки DELETE запросов товаров")
public class ProductDeleteTests {

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

    @DisplayName("Удаление продукта по заданному ID (позитивный тест)")
    @Test
    void deleteProductPositiveTest() throws IOException {

        DbUtils.getProductsMapper().deleteByPrimaryKey(Long.valueOf(productId));

        assertThat(DbUtils.getProductsMapper().selectByPrimaryKey(Long.valueOf(productId))).isNull();
    }

    @DisplayName("Удаление продукта по несуществующему ID (негативный тест)")
    @Test
    void deleteProductNegativeTest() throws IOException {

        retrofit2.Response<ResponseBody> response = productService
                .deleteProduct(faker.number().numberBetween(1, 1000))
                .execute();

        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.code()).isEqualTo(500);
        assertThat(response.errorBody().string().contains(Errors.CODE500.getMessage()));
        assertThat(DbUtils.getProductsMapper().selectByPrimaryKey(Long.valueOf(productId))).isNotNull();
    }

    @DisplayName("Удаление продукта без указания ID (негативный тест)")
    @Test
    void deleteProductWithoutIdNegativeTest() throws IOException {

        retrofit2.Response<ResponseBody> response = productService
                .deleteProductWithoutId()
                .execute();

        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.code()).isEqualTo(405);
        assertThat(response.errorBody().string().contains(Errors.CODE405.getMessage()));
        assertThat(DbUtils.getProductsMapper().selectByPrimaryKey(Long.valueOf(productId))).isNotNull();
    }

    @AfterEach
    void tearDown() throws IOException {

        DeleteProduct.finishTests(productId);
    }
}

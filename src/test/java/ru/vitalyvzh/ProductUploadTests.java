package ru.vitalyvzh;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import ru.vitalyvzh.base.enums.Errors;
import ru.vitalyvzh.db.dao.ProductsMapper;
import ru.vitalyvzh.dto.Product;
import ru.vitalyvzh.service.ProductService;
import ru.vitalyvzh.utils.CreateProduct;
import ru.vitalyvzh.utils.DbUtils;
import ru.vitalyvzh.utils.RetrofitUtils;
import ru.vitalyvzh.utils.DeleteProduct;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Проверки POST запросов товаров")
public class ProductUploadTests {

    Faker faker = new Faker();
    static ProductService productService;
    Product product;
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

        product = CreateProduct.createProduct();
        productId = product.getId();

    }

    @DisplayName("Добавление продукта (позитивный тест)")
    @Test
    void createNewProductPositiveTest() throws IOException {

        productId = CreateProduct.createProduct().getId();

        assertThat(productsMapper.selectByPrimaryKey(Long.valueOf(productId))).isNotNull();

    }

    @DisplayName("Добавление продукта (негативный тест)")
    @Test
    void createNewProductNegativeTest() throws IOException {
        retrofit2.Response<Product> response = productService
                .createProduct(product.withId(faker.number().numberBetween(1, 1000)))
                .execute();

        productId = product.getId();

        if(response.body() != null) {
            productId = response.body().getId();
        } else {
            productId = null;
        }

        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.errorBody().string()).contains(Errors.CODE400NULL.getMessage());
    }

    @AfterEach
    void tearDown() throws IOException {

        DeleteProduct.finishTests(productId, productService);
    }
}

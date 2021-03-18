package ru.vitalyvzh;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import ru.vitalyvzh.base.enums.CategoryType;
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

    @DisplayName("Добавление продукта (позитивный тест)")
    @Test
    void createNewProductPositiveTest() throws IOException {

        productId = CreateProduct.createProduct().getId();

        assertThat(productsMapper.selectByPrimaryKey(Long.valueOf(productId))).isNotNull();

    }

    @DisplayName("Добавление продукта (негативный тест)")
    @Test
    void createNewProductNegativeTest() throws IOException {
        product = new Product()
                .withId(faker.number().numberBetween(1, 1000))
                .withTitle(faker.aviation().aircraft())
                .withPrice(faker.number().numberBetween(10000, 25000))
                .withCategoryTitle(CategoryType.AUTOANDINDUSTRIAL.getTitle());

        retrofit2.Response<Product> response = productService
                .createProduct(product)
                .execute();

        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.errorBody().string()).contains(Errors.CODE400NULL.getMessage());
    }

    @AfterEach
    void tearDown() throws IOException {

        DeleteProduct.finishTests(productId);
    }
}

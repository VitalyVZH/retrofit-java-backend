package ru.vitalyvzh.utils;

import com.github.javafaker.Faker;
import retrofit2.Response;
import ru.vitalyvzh.base.enums.CategoryType;
import ru.vitalyvzh.dto.Product;
import ru.vitalyvzh.service.ProductService;

import java.io.IOException;

public class CreateProduct {

    static ProductService productService;
    static Response<Product> response;

    public static Product createProduct() throws IOException {

        Faker faker = new Faker();

        productService = RetrofitUtils
                .getRetrofit()
                .create(ProductService.class);

        Product product = new Product()
                .withTitle(faker.food().fruit())
                .withPrice(faker.number().numberBetween(1, 1000))
                .withCategoryTitle(CategoryType.AUTOANDINDUSTRIAL.getTitle());

        response = productService
                .createProduct(product)
                .execute();

        return response.body();
    }
}

package ru.vitalyvzh.utils;

import com.github.javafaker.Faker;
import ru.vitalyvzh.base.enums.CategoryType;
import ru.vitalyvzh.dto.Product;

public class SetUp {

    public static Product createProduct() {

        Product product;
        Faker faker = new Faker();

        product = new Product()
                .withTitle(faker.food().fruit())
                .withPrice((int) (Math.random() * 1000 + 1))
                .withCategoryTitle(CategoryType.FOOD.getTitle());

        return product;
    }
}

package ru.vitalyvzh;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.vitalyvzh.base.enums.Errors;
import ru.vitalyvzh.dto.Category;
import ru.vitalyvzh.service.CategoryService;
import ru.vitalyvzh.utils.DbUtils;
import ru.vitalyvzh.utils.RetrofitUtils;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vitalyvzh.base.enums.CategoryType.FOOD;

@DisplayName("Проверки категорий товаров")
public class CategoryGetTests {

    static CategoryService categoryService;
    Faker faker = new Faker();

    @BeforeAll
    static void beforeAll() throws MalformedURLException {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @DisplayName("Позитивная проверка категории Food")
    @Test
    void getFoodCategoryPositiveTest() throws IOException {

        Response<Category> response = categoryService
                .getCategory(FOOD.getId())
                .execute();
        assertThat(response.isSuccessful()).isTrue(); // код от 200 до 300
        assertThat(response.body().getId()).as("Id is not equal to 1!").isEqualTo(1);
        assertThat(response.body().getTitle()).as("Title is not Food").isEqualTo(FOOD.getTitle());
    }

    @DisplayName("Негативная проверка категорий")
    @Test
    void getFoodCategoryNegativeTest() throws IOException {
        Response<Category> response = categoryService
                .getCategory(faker.number().numberBetween(3,100))
                .execute();
        assertThat(response.code()).isEqualTo(404);
        assertThat(response.errorBody().string()).contains(Errors.CODE404CAT.getMessage());
    }
}

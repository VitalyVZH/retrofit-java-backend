package ru.vitalyvzh;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.vitalyvzh.base.enums.CategoryType;
import ru.vitalyvzh.dto.Category;
import ru.vitalyvzh.service.CategoryService;
import ru.vitalyvzh.utils.RetrofitUtils;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vitalyvzh.base.enums.CategoryType.FOOD;

@DisplayName("Проверки категорий товаров")
public class CategoryTests {

    static CategoryService categoryService;

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
}

package ru.vitalyvzh;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.vitalyvzh.base.enums.Errors;
import ru.vitalyvzh.dto.Category;
import ru.vitalyvzh.service.CategoryService;
import ru.vitalyvzh.utils.RetrofitUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vitalyvzh.base.enums.CategoryType.AUTOANDINDUSTRIAL;

@DisplayName("Проверки GET запросов категорий")
public class CategoryGetTests {

    static CategoryService categoryService;
    Faker faker = new Faker();

    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @DisplayName("Запрос категории (позитивный тест)")
    @Description("Проверка категории Food по существующему ID")
    @Test
    void getFoodCategoryPositiveTest() throws IOException {

        Response<Category> response = categoryService
                .getCategory(AUTOANDINDUSTRIAL.getId())
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body().getId()).as("Id is not equal to 38!").isEqualTo(38);
        assertThat(response.body().getTitle()).as("Title is not Auto & Industrial").isEqualTo(AUTOANDINDUSTRIAL.getTitle());
    }

    @DisplayName("Запрос категории (негативный тест)")
    @Description("Проверка категории по несуществующему ID")
    @Test
    void getFoodCategoryNegativeTest() throws IOException {
        Response<Category> response = categoryService
                .getCategory(faker.number().numberBetween(3,100))
                .execute();
        assertThat(response.code()).isEqualTo(404);
        assertThat(response.errorBody().string()).contains(Errors.CODE404CAT.getMessage());
    }
}

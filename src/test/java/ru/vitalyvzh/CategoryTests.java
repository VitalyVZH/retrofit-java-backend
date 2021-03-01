package ru.vitalyvzh;

import org.junit.jupiter.api.BeforeAll;
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

public class CategoryTests {

    static Retrofit retrofit;

    @BeforeAll
    static void beforeAll() throws MalformedURLException {
        retrofit = RetrofitUtils.getRetrofit();
    }

    @Test
    void getCategoryPositiveTest() throws IOException {
        Response<Category> response =
                retrofit.create(CategoryService.class).getCategory(CategoryType.FOOD.getId()).execute();
        assertThat(response.body().getId()).as("Response is null!").isEqualTo(1);
    }
}

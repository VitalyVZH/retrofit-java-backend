package ru.vitalyvzh.utils;

import okhttp3.ResponseBody;
import ru.vitalyvzh.service.ProductService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TearDown {
    public static void finishTests(Integer productId, ProductService productService) {

        if(productId != null) {
            try {
                retrofit2.Response<ResponseBody> response = productService
                        .deleteProduct(productId)
                        .execute();
                assertThat(response.isSuccessful()).isTrue();
                assertThat(response.code()).isEqualTo(200);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

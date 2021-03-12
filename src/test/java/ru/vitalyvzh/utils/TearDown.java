package ru.vitalyvzh.utils;

import okhttp3.ResponseBody;
import ru.vitalyvzh.service.ProductService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TearDown {
    public static void finishTests(Integer productId, ProductService productService) throws IOException {

        if(productId != null) {
            DbUtils.getProductsMapper().deleteByPrimaryKey(Long.valueOf(productId));
            assertThat(DbUtils.getProductsMapper().selectByPrimaryKey(Long.valueOf(productId))).isNull();
//            try {
//                retrofit2.Response<ResponseBody> response = productService
//                        .deleteProduct(productId)
//                        .execute();
//                assertThat(response.isSuccessful()).isTrue();
//                assertThat(response.code()).isEqualTo(200);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

    }
}

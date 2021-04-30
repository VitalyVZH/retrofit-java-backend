package ru.vitalyvzh.utils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteProduct {
    public static void finishTests(Integer productId) throws IOException {

        if(productId != null) {
            DbUtils.getProductsMapper()
                    .deleteByPrimaryKey(Long.valueOf(productId));
            assertThat(DbUtils.getProductsMapper()
                    .selectByPrimaryKey(Long.valueOf(productId))).isNull();

        }
    }
}

package ru.vitalyvzh.service;

import okhttp3.ResponseBody;
import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.Empty;
import retrofit2.Call;
import retrofit2.http.*;
import ru.vitalyvzh.dto.Category;
import ru.vitalyvzh.dto.Product;

public interface ProductService {

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") Integer id);

    @GET("products")
    Call<Product> getProduct();

    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @PUT("products")
    Call<ResponseBody> updateProduct(@Body Product updateProductRequest);

    @PUT("products/{id}")
    Call<ResponseBody> updateProduct(@Path("id") Integer id, @Body Product updateProductRequest);


    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);


}

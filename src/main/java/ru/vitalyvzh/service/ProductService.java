package ru.vitalyvzh.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import ru.vitalyvzh.dto.Category;
import ru.vitalyvzh.dto.Product;

public interface ProductService {

    @GET("products/{id}")
    Call<Product> getCategory(@Path("id") Integer id);

    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);


}

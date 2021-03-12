package ru.vitalyvzh.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import ru.vitalyvzh.dto.Product;

public interface ProductService {

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") Integer id);

    @GET("products")
    Call<Product[]> getProducts();

    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @PUT("products")
    Call<Product> updateProduct(@Body Product updateProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") Integer id);

    @DELETE("products")
    Call<ResponseBody> deleteProductWithoutId();


}

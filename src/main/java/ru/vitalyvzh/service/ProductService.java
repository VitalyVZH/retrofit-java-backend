package ru.vitalyvzh.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.vitalyvzh.dto.Product;

public interface ProductService {

    @POST("/market/api/v1/products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @DELETE("/market/api/v1/products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);


}

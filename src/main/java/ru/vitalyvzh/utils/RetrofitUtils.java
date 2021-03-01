package ru.vitalyvzh.utils;

import lombok.experimental.UtilityClass;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@UtilityClass
public class RetrofitUtils {

    public Retrofit getRetrofit() throws MalformedURLException {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMinutes(1l))
                .callTimeout(Duration.ofMinutes(1l))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return new Retrofit.Builder()
            .baseUrl(new URL("http://80.78.248.82:8189/market/api/v1/"))
            .client(client)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    }
}

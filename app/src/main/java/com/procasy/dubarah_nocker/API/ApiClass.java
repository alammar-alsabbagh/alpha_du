package com.procasy.dubarah_nocker.API;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Omar on 7/28/2016.
 */
public class ApiClass {

    public static final String BASE_URL = "http://nockerapp.com/index.php/API/";
    public static final String Pic_Base_URL = "http://nockerapp.com/";
    private static Retrofit retrofit = null;



    public static Retrofit getClient() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder().addHeader("Authorization", "key = AIzaSyAZrkMyfg8-LlufNrESiGOOworfzzi94A4").build();
                return chain.proceed(newRequest);
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();


        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

}

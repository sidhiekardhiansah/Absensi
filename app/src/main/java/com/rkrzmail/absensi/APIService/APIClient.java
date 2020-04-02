
package com.rkrzmail.absensi.APIService;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rkrzmail.absensi.utility.StringConverter;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 1/10/2018.
 */

public class APIClient implements Interceptor {

    private static Retrofit retrofit = null;

    private String credentials;

    public APIClient (String Username, String Password) {
        this.credentials = Credentials.basic(Username, Password);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .addHeader("X-Api-Key", "4ad75498f665ec44c5b91e70c3cf6698")
                .header("Authorization", credentials)
                .build();
        return chain.proceed(authenticatedRequest);
    }

    public static Retrofit getClient() {
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(new APIClient("admindika", "B3ndh1L2019"))
                .build();

        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(String.class, new StringConverter());
        Gson gson = gb.create();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://rest-api.ptdika.com/api/user/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }
}
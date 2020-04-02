
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

public class APIClient2 implements Interceptor{

    private static Retrofit retrofit = null;

    private String credentials;

    public APIClient2 (String Username, String Password) {
        this.credentials = Credentials.basic(Username, Password);
    }

@Override
public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    Request authenticatedRequest = request.newBuilder()
            .addHeader("X-Api-Key", "sidik123")
            .header("Authorization", credentials)
            .build();
    return chain.proceed(authenticatedRequest);
}

    public static Retrofit getClient() {
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(new APIClient("admin", "1234"))
                .build();
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(String.class, new StringConverter());
        Gson gson = gb.create();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://tms.ptdika.com/api/index.php/api/absen/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }
}
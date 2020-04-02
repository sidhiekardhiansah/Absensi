
package com.rkrzmail.absensi.APIService;

/**
 * Created by user on 1/10/2018.
 */


import com.rkrzmail.absensi.model.Absen.PostAbsen;
import com.rkrzmail.absensi.model.Login.ModelLogin;
import com.rkrzmail.absensi.model.dataabsen.PostAbsensi;
import com.rkrzmail.absensi.model.param.ModelParam;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;


public interface APIInterfacesRest {



//    @GET("login_sales?username=K1001005&password=1981-04-17")
//    Call<ModelLogin> getData(@Query("x-api-key") String apikey);

    @GET("login_sales")
    Call<ModelLogin> getData(@QueryMap Map<String, String> params1, @QueryMap Map<String, String> params2);

    @Multipart
    @POST("absenn")
    Call<PostAbsen> sendDataAbsen(
            @Part("sales_code") RequestBody sales_code,
            @Part("name") RequestBody name,
            @Part("unit") RequestBody unit,
            @Part("branch") RequestBody branch,
            @Part("position") RequestBody position,
            @Part MultipartBody.Part foto,
            @Part("created_date_foto") RequestBody created_date_foto,
            @Part("kategori") RequestBody kategori,
            @Part("geo_info") RequestBody geo_info,
            @Part("shift_name") RequestBody shift_name,
            @Part("longshift_status") RequestBody longshift_status,
            @Part("approved_status") RequestBody approved_status,
            @Part("approved_by") RequestBody approved_by,
            @Part("approved_date") RequestBody approved_date,
            @Part("created_date") RequestBody created_date,
            @Part("keterangan") RequestBody keterangan
    );

    @GET("shift")
    Call<ModelParam> getParameter();

}
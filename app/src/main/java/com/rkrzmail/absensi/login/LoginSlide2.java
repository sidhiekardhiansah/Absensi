
package com.rkrzmail.absensi.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rkrzmail.absensi.APIService.APIClient;
import com.rkrzmail.absensi.APIService.APIInterfacesRest;
import com.rkrzmail.absensi.Activity.Home;
import com.rkrzmail.absensi.R;
import com.rkrzmail.absensi.model.Login.ModelLogin;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSlide2 extends AppCompatActivity {

    ModelLogin dataModelUser;
    SharedPreferences pref;

    EditText un, pw;
    Button btnLogin;
    String username, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layar2);

        un = findViewById(R.id.username);
        pw = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        // pref = getSharedPreferences(PREFS_NAME, 0);

//        getData();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("username", un.getText().toString());
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("password", pw.getText().toString());

//                a.putExtra("datauser", (Parcelable) dataModelUser);

                if ((un.getText().toString() != null) && (pw.getText().toString() != null)) {
                    //There is a mapping for username and password.

                    getData(params1, params2);

                    //a.putExtra("datauser", (Parcelable) dataModelUser);


                    //login successful
                }

                //No mapping for username or password.


//                if (!un.getText().toString().isEmpty() && !pw.getText().toString().isEmpty()) {
//                    boolean isValid = false;
//                    if (username.equalsIgnoreCase(un.getText().toString()) && password.equalsIgnoreCase(pw.getText().toString())) {
//                        Intent a = new Intent(getActivity(), Home.class);
//                        a.putExtra("datauser", (Parcelable) dataModelUser);
//                        startActivity(a);
//
//                        Toast.makeText(getActivity(),
//                                "login Succes",
//                                Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getActivity(),
//                                "akun belum terdaftar",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                } else if (!un.getText().toString().isEmpty()) {
//                    Toast.makeText(getActivity(),
//                            "please fill any empty field",
//                            Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }





    public void getData(Map<String, String> params1, Map<String, String> params2) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelLogin> data = apiInterface.getData(params1, params2);

        data.enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {

                dataModelUser = response.body();

                if (dataModelUser != null){
                    pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    pref.edit().putString("name", dataModelUser.getData().getName().toString()).commit();
                    pref.edit().putString("branch", dataModelUser.getData().getBranch().toString()).commit();
                    pref.edit().putString("unit", dataModelUser.getData().getUnit().toString()).commit();
                    pref.edit().putString("position", dataModelUser.getData().getPosition().toString()).commit();
                    pref.edit().putString("nik", dataModelUser.getData().getNIK().toString()).commit();
                    Intent a = new Intent(getApplicationContext(), Home.class);

                    startActivity(a);
//                    Toast.makeText(getApplicationContext(),
//                            "login Succes",
//                            Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Koneksi Berhasil", Toast.LENGTH_SHORT).show();
                } else
                {
                    // getData(params1, params2);
                    Toast.makeText(getApplicationContext(),
                            "login gagal",
                            Toast.LENGTH_SHORT).show();
                }

//                username = dataModelUser.getData().getSMCode();
//                password = dataModelUser.getData().getDateOfBirth();


            }
            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }




}

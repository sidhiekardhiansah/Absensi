package com.rkrzmail.absensi.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.rkrzmail.absensi.APIService.APIClient2;
import com.rkrzmail.absensi.APIService.APIInterfacesRest;
import com.rkrzmail.absensi.APIService.AppUtil;
import com.rkrzmail.absensi.R;
import com.rkrzmail.absensi.model.Absen.PostAbsen;
import com.rkrzmail.absensi.model.Login.ModelLogin;
import com.rkrzmail.absensi.model.dataabsen.PostAbsensi;
import com.rkrzmail.absensi.model.param.ModelParam;
import com.rkrzmail.absensi.model.parameter.DataParameter;
import com.robin.locationgetter.EasyLocation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class ActivityIzin extends AppCompatActivity implements LocationListener {

    Button  btnKirim;
    ImageView imageView;
    EditText txtKeterangan, txtlocation2;
    Bitmap foto, bitmap;
    ImageButton btnTakePicture;
    Spinner spinnerShift;
    String Gname, Gunit, Gbranch, Gposition, GNIK;
    TextView txtlocation;
    LocationManager locationManager;
    String latitude, longitude, filePath;
    private static final int REQUEST_LOCATION=1;
    protected LocationListener locationListener;
    double latitude2;
    double longitude2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izin);
        txtlocation= findViewById(R.id.location);
        txtlocation2= findViewById(R.id.location2);
        txtlocation2.setText(latitude+","+longitude);
        spinnerShift= findViewById(R.id.spinnershift);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gname = pref.getString("name", "name");
        Gunit = pref.getString("unit", "unit");
        Gbranch = pref.getString("branch", "branch");
        Gposition = pref.getString("position", "position");
        GNIK = pref.getString("nik", "nik");

        btnTakePicture = findViewById(R.id.btnTakePicture);
        btnKirim = findViewById(R.id.btnkirim);
        imageView = findViewById(R.id.imageview);
        txtKeterangan = findViewById(R.id.txtketerangan);
        initSpinnerShift();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        spinnerShift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedShift= parent.getItemAtPosition(position).toString();

                Toast.makeText(ActivityIzin.this , "kamu sedang masuk shift" + selectedShift, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        if (Build.VERSION.SDK_INT>=23){
            requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }
        locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            onGPS();
        } else {
            //gps already exist
            getLocation();
        }
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturepic();
            }
        });

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtlocation2.getText().toString().equalsIgnoreCase("null,null")){
                    Toast.makeText(ActivityIzin.this, "silahkan nyalakan permission location", Toast.LENGTH_SHORT).show();
                } else {
                    if (imageView.getDrawable() == null) {
                        Toast.makeText(ActivityIzin.this, "foto blm di isi", Toast.LENGTH_SHORT).show();
                    }
                    if (txtKeterangan.getText().toString().length() == 0) {
                        txtKeterangan.setError("keterangan belum di isi !");
                    }
                    if (imageView.getDrawable() == null && txtKeterangan.getText().toString().length() == 0) {
                        txtKeterangan.setError("keterangan belum di isi !");
                        Toast.makeText(ActivityIzin.this, "foto blm di isi", Toast.LENGTH_SHORT).show();
                    }
                    if (txtKeterangan.getText().toString().length() != 0 && imageView.getDrawable() != null) {
                        sendDataAbsen();
                        onBackPressed();
                    }
                }
            }
        });
    }

    private void onGPS() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Enabled GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });//oke
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    public void getLocation(){
        if(ActivityCompat.checkSelfPermission(ActivityIzin.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityIzin.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else{
            Location locationGPS= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork= locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location locationPassive= locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if(locationGPS != null){
                double lat= locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                txtlocation2.setText(latitude+","+longitude);
                //txtlocation.setText("your location"+"\n"+"latitude"+latitude+"\n"+"longitude"+longitude);
            } else if(locationPassive != null){
                double lat= locationPassive.getLatitude();
                double longi = locationPassive.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                txtlocation2.setText(latitude+","+longitude);
               // txtlocation.setText("your location"+"\n"+"latitude"+latitude+"\n"+"longitude"+longitude);
            } else if(locationNetwork != null){
                double lat= locationNetwork.getLatitude();
                double longi = locationNetwork.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                txtlocation2.setText(latitude+","+longitude);
               // txtlocation.setText("your location"+"\n"+"latitude"+latitude+"\n"+"longitude"+longitude);
            } else {
                Toast.makeText(this, "Cant get your location", Toast.LENGTH_SHORT).show();
            }
            //try run
        }
    }

        private void capturepic()
    {
        Intent intent = new Intent(this, ImageSelectActivity.class);
        intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
        startActivityForResult(intent, 1213);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1213 && resultCode == Activity.RESULT_OK) {
            filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
            imageView.setImageBitmap(selectedImage);
            foto= bitmap;
        }
    }
// ini kan lu mau maksa user aktifin gPS NYA nah itu lu udh bikin fungsi nya, bukannya dipanggil lagi ya di atas nya , kaya sendData Absen ini kan udh lu bikin fungsi nya trus lu panggil di atas
    //send post data with image
    private void sendDataAbsen() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String now = formatter.format(new Date());
        File foto = new File(filePath);
       // File foto = createTempFile(bitmap);
       // byte[] bImg1 = AppUtil.FiletoByteArray(foto);
        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"),foto);
        MultipartBody.Part fotox = MultipartBody.Part.createFormData("foto", foto.getName() + ".jpg", requestFile1);
        String lspinner= spinnerShift.getSelectedItem().toString();
        String[] shift = lspinner.split(" ");
        String spinner = shift[0];
        APIInterfacesRest apiInterface = APIClient2.getClient().create(APIInterfacesRest.class);
        Call<PostAbsen> postAdd = apiInterface.sendDataAbsen(
                toRequestBody(AppUtil.replaceNull(GNIK)),
                toRequestBody(AppUtil.replaceNull(Gname)),
                toRequestBody(AppUtil.replaceNull(Gunit)),
                toRequestBody(AppUtil.replaceNull(Gbranch)),
                toRequestBody(AppUtil.replaceNull(Gposition)),
                fotox,
                toRequestBody(AppUtil.replaceNull(now)),
                toRequestBody(AppUtil.replaceNull("IZIN")),
                toRequestBody(AppUtil.replaceNull(latitude+","+longitude)),
                toRequestBody(AppUtil.replaceNull(spinner)),
                toRequestBody(AppUtil.replaceNull(" ")),
                toRequestBody(AppUtil.replaceNull(" - ")),
                toRequestBody(AppUtil.replaceNull(" - ")),
                toRequestBody(AppUtil.replaceNull("0000-00-00 00:00:00")),
                toRequestBody(AppUtil.replaceNull(now)),
                toRequestBody(AppUtil.replaceNull(txtKeterangan.getText().toString()))
        );

        postAdd.enqueue(new Callback<PostAbsen>() {
            @Override
            public void onResponse(Call<PostAbsen> call, Response<PostAbsen> response) {

                PostAbsen responServer = response.body();

                if (responServer != null) {
                    Toast.makeText(ActivityIzin.this,responServer.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<PostAbsen> call, Throwable t) {

                Toast.makeText(ActivityIzin.this, "Maaf koneksi bermasalah", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });

    }

    //change string to requestbody
    public RequestBody toRequestBody(String value) {
        if (value == null) {
            value = "";
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }


    public void onBackPressed() {
        finish();
    }

    private void initSpinnerShift(){
        final APIInterfacesRest apiInterface = APIClient2.getClient().create(APIInterfacesRest.class);
        final Call<ModelParam> data = apiInterface.getParameter();
        data.enqueue(new Callback<ModelParam>() {
            @Override
            public void onResponse(Call<ModelParam> call, Response<ModelParam> response) {
                if (response.isSuccessful()) {
                    ModelParam listdata = response.body();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < listdata.getData().size(); i++){
                        if(Gunit.equals(listdata.getData().get(i).getUnit())){
                            String name= listdata.getData().get(i).getShiftName();
                            String mulai= listdata.getData().get(i).getJamMulai();
                            String selesai= listdata.getData().get(i).getJamSelesai();
                            //listSpinner.add(name);
                            listSpinner.add(name+"  " +mulai+ "  "+ selesai);
                            //listSpinner.add(selesai);
                        }
                    }
                    listSpinner.add(0, "- SELECT TYPE -");
                    listSpinner.add(1, "Non-Shift");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityIzin.this,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerShift.setAdapter(adapter);
                } else {
                    Toast.makeText(ActivityIzin.this, "Gagal mengambil data shift", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelParam> call, Throwable t) {
                Toast.makeText(ActivityIzin.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        txtlocation = (TextView) findViewById(R.id.location);
        latitude2 =location.getLatitude();
        longitude2=location.getLongitude();
       // txtlocation.setText("Latitude:" + latitude2 + ", Longitude:" + longitude2);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","status");
    }
}

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

import com.rkrzmail.absensi.APIService.APIClient2;
import com.rkrzmail.absensi.APIService.APIInterfacesRest;
import com.rkrzmail.absensi.APIService.AppUtil;
import com.rkrzmail.absensi.R;
import com.rkrzmail.absensi.model.Absen.PostAbsen;
import com.rkrzmail.absensi.model.dataabsen.PostAbsensi;
import com.rkrzmail.absensi.model.param.ModelParam;

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

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class ActivityMasuk extends AppCompatActivity implements LocationListener {

    private static final int REQUEST_LOCATION = 1;

    Button btnKirim;
    ImageButton btnTakePicture;
    ImageView imageView;
    EditText txtKeterangan;
    Bitmap foto, bitmap;
    Spinner spinnerShift;
    String Gname, Gunit, Gbranch, Gposition, GNIK;
    TextView txtlocation;
    LocationManager locationManager;
    String latitude, longitude, filePath;
    List<String> listSpinner = new ArrayList<String>();
    private static final int CAMERA_CAPTURE = 1;
    private Uri fileUri;
    protected LocationListener locationListener;
    double latitude2;
    double longitude2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gname = pref.getString("name", "name");
        Gunit = pref.getString("unit", "unit");
        Gbranch = pref.getString("branch", "branch");
        Gposition = pref.getString("position", "position");
        GNIK = pref.getString("nik", "nik");

        // dataModelUser = getIntent().getParcelableExtra("datauser");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        initSpinnerShift();
        btnTakePicture = findViewById(R.id.btnTakePicture);
        btnKirim = findViewById(R.id.btnkirim);
        imageView = findViewById(R.id.imageview);
        txtKeterangan = findViewById(R.id.txtketerangan);
        spinnerShift = findViewById(R.id.spinnershift);
        txtlocation = findViewById(R.id.location);
        spinnerShift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedShift = parent.getItemAtPosition(position).toString();
                Toast.makeText(ActivityMasuk.this, "kamu sedang masuk shift" + selectedShift, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturepic();
            }
        });

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            onGPS();
        } else {
            //gps already exist
            getLocation();
        }

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageView.getDrawable() == null) {
                    Toast.makeText(ActivityMasuk.this, "foto blm di isi", Toast.LENGTH_SHORT).show();
                }
                if (txtKeterangan.getText().toString().length() == 0) {
                    txtKeterangan.setError("keterangan belum di isi !");
                }
                if (imageView.getDrawable() == null && txtKeterangan.getText().toString().length() == 0) {
                    txtKeterangan.setError("keterangan belum di isi !");
                    Toast.makeText(ActivityMasuk.this, "foto blm di isi", Toast.LENGTH_SHORT).show();

                }
                if (txtKeterangan.getText().toString().length() != 0 && imageView.getDrawable() != null) {
                    sendDataAbsen();
                    onBackPressed();
                }
            }
        });
    }



    private void onGPS() {
            LocationManager locationManager = null;
            boolean gps_enabled = false;
            boolean network_enabled = false;
            if ( locationManager == null ) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            }
            try {
                gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex){}
            try {
               network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex){}
            if ( !gps_enabled && !network_enabled ){
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityMasuk.this);
                dialog.setMessage("GPS not enabled");
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //this will navigate user to the device location settings screen
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
            }
        }


    public void getLocation(){
        if(ActivityCompat.checkSelfPermission(ActivityMasuk.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityMasuk.this,
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
                //txtlocation.setText("your location"+"\n"+"latitude"+latitude+"\n"+"longitude"+longitude);
            } else if(locationPassive != null){
                double lat= locationPassive.getLatitude();
                double longi = locationPassive.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                //txtlocation.setText("your location"+"\n"+"latitude"+latitude+"\n"+"longitude"+longitude);
            } else if(locationNetwork != null){
                double lat= locationNetwork.getLatitude();
                double longi = locationNetwork.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                //txtlocation.setText("your location"+"\n"+"latitude"+latitude+"\n"+"longitude"+longitude);
            } else {
                Toast.makeText(this, "Cant get your location", Toast.LENGTH_SHORT).show();
            }
            //try run
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    private void capturepic()
    {
        Intent intent = new Intent(this, ImageSelectActivity.class);
        intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
        startActivityForResult(intent, 1213);
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//        startActivityForResult(intent, CAMERA_CAPTURE);
    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    private void capturedImage() {
        thread2();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1213 && resultCode == Activity.RESULT_OK) {
            filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
            imageView.setImageBitmap(selectedImage);

        }

//        if (requestCode == CAMERA_CAPTURE) {
//            if (resultCode == RESULT_OK) {
//                capturedImage();
//            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(ActivityMasuk.this,
//                        "User cancelled image capture", Toast.LENGTH_SHORT)
//                        .show();
//            } else {
//                Toast.makeText(ActivityMasuk.this,
//                        "Failed to capture image", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }
    }

    private void thread2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                            options);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(270);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                            foto= bitmap;
                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


        //send post data with image
    private void sendDataAbsen() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String now = formatter.format(new Date());

        //File foto2 = createTempFile(bitmap);
        File foto2 = new File(filePath);
       // byte[] bImg1 = AppUtil.FiletoByteArray(foto2);
        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"), foto2);
        MultipartBody.Part fotox = MultipartBody.Part.createFormData("foto", foto2.getName() + ".jpg", requestFile1);

        String lspinner = spinnerShift.getSelectedItem().toString();
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
                toRequestBody(AppUtil.replaceNull("MASUK")),
                toRequestBody(AppUtil.replaceNull(latitude + "," + longitude)),
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
                    Toast.makeText(ActivityMasuk.this, responServer.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PostAbsen> call, Throwable t) {

                Toast.makeText(ActivityMasuk.this, "Maaf koneksi bermasalah", Toast.LENGTH_LONG).show();
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

    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() + "");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
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
                    //  datashift = response.body().getData().getRefParameterShift();
                    ModelParam listdata = response.body();
                    listSpinner = new ArrayList<String>();
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityMasuk.this,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //spinnerShift.setAdapter(adapter);
                    spinnerShift.setAdapter(adapter);
                } else {
                    Toast.makeText(ActivityMasuk.this, "Gagal mengambil data shift", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelParam> call, Throwable t) {

                Toast.makeText(ActivityMasuk.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onLocationChanged(Location location) {
        txtlocation = (TextView) findViewById(R.id.location);
        latitude2 =location.getLatitude();
        longitude2=location.getLongitude();
      //  txtlocation.setText("Latitude:" + latitude2 + ", Longitude:" + longitude2);
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

package com.rkrzmail.absensi.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.rkrzmail.absensi.R;


public class Home extends AppCompatActivity {

    CardView cardMasuk, cardKeluar, cardSakit, cardIzin, cardOff;
    ImageButton btnMasuk, btnKeluar, btnSakit, btnIzin, btnOff;
    Button btnLogout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        btnMasuk = findViewById(R.id.btnMasuk);
        btnKeluar = findViewById(R.id.btnKeluar);
        btnSakit = findViewById(R.id.btnSakit);
        btnIzin = findViewById(R.id.btnIzin);
        btnOff = findViewById(R.id.btnOff);
        btnLogout = findViewById(R.id.btnLogout);
        cardMasuk = findViewById(R.id.cardMasuk);
        cardKeluar = findViewById(R.id.cardKeluar);
        cardSakit = findViewById(R.id.cardSakit);
        cardIzin = findViewById(R.id.cardIzin);
        cardOff = findViewById(R.id.cardOff);

        if (Build.VERSION.SDK_INT>=23){
            requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardMasuk.setEnabled(false);
                cardMasuk.setClickable(false);
                btnMasuk.setClickable(false);
                btnMasuk.setEnabled(false);

                Intent a = new Intent(Home.this, ActivityMasuk.class);
                startActivity(a);
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardMasuk.setEnabled(false);
                cardMasuk.setClickable(false);
                btnKeluar.setClickable(false);
                btnKeluar.setEnabled(false);
                Intent a = new Intent(Home.this, ActivityKeluar.class);
                startActivity(a);
            }
        });

        btnSakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Home.this, ActivitySakit.class);
                startActivity(a);
            }
        });

        btnIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Home.this, ActivityIzin.class);
                startActivity(a);
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Home.this, ActivityOff.class);
                startActivity(a);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setMessage("Yakin Logout ?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        finish();
                    }
                })

                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog =builder.create();
        alertDialog.show();
    }

}

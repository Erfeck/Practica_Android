package com.example.util_idades.permisos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.util_idades.R;
import com.example.util_idades.SelectorTemas;
import com.example.util_idades.permisos.bd.PermisosDatabase;
import com.example.util_idades.util.Util;

import java.util.Calendar;

public class PermisosActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SOLICITUD_PERMISO_CAMARA = 1;
    private boolean haPermitidoCamara = false;
    Button botonHacerFoto, botonVerFotos;
    ImageView imagenFoto;
    PermisosDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectorTemas.aplicarTema(this);
        setContentView(R.layout.activity_permisos);
        getSupportActionBar().hide();

        db = new PermisosDatabase(this);

        botonHacerFoto = findViewById(R.id.bPermHacerFotos);
        botonHacerFoto.setOnClickListener(this);

        botonVerFotos = findViewById(R.id.bPermVerFotos);
        botonVerFotos.setOnClickListener(this);

        imagenFoto = findViewById(R.id.imgPermFoto);
    }

    @Override
    protected void onResume() {
        super.onResume();
        verificarPermisos();

        if (haPermitidoCamara) {
            if (!db.getFotos().isEmpty()) {
                imagenFoto.setImageBitmap(db.getFotoDelDia(obtenerFechaDiaDeHoy()));
                botonVerFotos.setVisibility(View.VISIBLE);
            } else {
                botonVerFotos.setVisibility(View.GONE);
            }
        } else {
            imagenFoto.setImageDrawable(getResources().getDrawable(R.drawable.permisos_nopermission));
            botonVerFotos.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bPermHacerFotos:
                if (haPermitidoCamara) {
                    abrirCamara();
                } else {
                    Toast.makeText(this, "No has dado permisos", Toast.LENGTH_SHORT).show();
                    redirigirAConfiguracion();
                }
                break;
            case R.id.bPermVerFotos:
                Intent intent = new Intent(this, PermisosFotosActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SOLICITUD_PERMISO_CAMARA && resultCode == RESULT_OK) {
            // Obtener la imagen de la cámara
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imagenFoto.setImageBitmap(imageBitmap);

            if (db.existeFotoDeEseDia(obtenerFechaDiaDeHoy())) {
                db.actualizarFoto(imageBitmap, obtenerFechaDiaDeHoy());
            } else {
                db.agregarFoto(imageBitmap, obtenerFechaDiaDeHoy());
            }
        }
    }

    private void verificarPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            haPermitidoCamara = true;
        } else {
            Util.solicitarPermisos(
                    Manifest.permission.CAMERA,
                    "Si no concedes el permiso no hay foto",
                    SOLICITUD_PERMISO_CAMARA,
                    this);
        }
    }

    private void abrirCamara() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camaraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(camaraIntent, SOLICITUD_PERMISO_CAMARA);
        }
    }

    private String obtenerFechaDiaDeHoy() {
        Calendar calendario = Calendar.getInstance();
        int year = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        String fecha = dia + "/" + (mes + 1) + "/" + year;
        return fecha;
    }

    private void redirigirAConfiguracion() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
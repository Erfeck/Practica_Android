package com.example.util_idades.listacompra;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.util_idades.R;
import com.example.util_idades.SelectorTemas;
import com.example.util_idades.listacompra.bd.CompraDatabase;
import com.example.util_idades.listacompra.productos.Categoria;
import com.example.util_idades.util.Util;

public class CompraAddCtgActivity extends AppCompatActivity implements View.OnClickListener {

    final static int SOLICITUD_PERMISO_GALERIA = 1;
    private boolean haSeleccionadoImagen = false;
    CompraDatabase db;
    ImageView imagen;
    EditText etxtCtg;
    Button bCrearCtg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectorTemas.aplicarTema(this);
        setContentView(R.layout.activity_compra_add_ctg);
        getSupportActionBar().hide();

        db = new CompraDatabase(this);

        etxtCtg = findViewById(R.id.etxtAddCtgNombre);

        imagen = findViewById(R.id.imgAddCtg);
        imagen.setOnClickListener(this);

        bCrearCtg = findViewById(R.id.bAddCtgCrear);
        bCrearCtg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAddCtg:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                    abrirGaleria();
                } else {
                    Util.solicitarPermisos(
                            Manifest.permission.READ_MEDIA_IMAGES,
                            "Necesitamos acceso a la galería para que puedas seleccionar una imagen.",
                            SOLICITUD_PERMISO_GALERIA,
                            this);
                }
                break;
            case R.id.bAddCtgCrear:
                if (!haSeleccionadoImagen) {
                    Toast.makeText(getApplicationContext(), "Debes Elegir una foto", Toast.LENGTH_LONG).show();
                    return;
                }
                Bitmap imagenEnBitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
                String nombreCategoria = etxtCtg.getText().toString();

                if (nombreCategoria.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No puedes dejar el nombre vacío", Toast.LENGTH_LONG).show();
                    return;
                }

                nombreCategoria = nombreCategoria.trim();
                if (db.existeNombreCategoria(nombreCategoria)) {
                    Toast.makeText(getApplicationContext(), "Ese nombre ya existe", Toast.LENGTH_LONG).show();
                    return;
                }

                Categoria nuevaCategoria = new Categoria(nombreCategoria, imagenEnBitmap);
                db.nuevaCategoria(nuevaCategoria);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SOLICITUD_PERMISO_GALERIA && resultCode == RESULT_OK && data != null) {
            Uri imagenSeleccionada = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(imagenSeleccionada, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                imagen.setImageBitmap(bitmap);
                haSeleccionadoImagen = true;
            } else {
                Toast.makeText(this, "No se pudo obtener la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SOLICITUD_PERMISO_GALERIA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirGaleria();
            } else {
                Toast.makeText(this, "Permiso denegado permanentemente. Habilítalo desde Configuración.", Toast.LENGTH_LONG).show();
                redirigirAConfiguracion();
            }
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SOLICITUD_PERMISO_GALERIA);
    }

    private void redirigirAConfiguracion() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
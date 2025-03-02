package com.example.util_idades.permisos;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.util_idades.R;
import com.example.util_idades.SelectorTemas;
import com.example.util_idades.permisos.bd.PermisosDatabase;

import java.util.Calendar;

public class PermisosFotosActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer;
    int year;
    int mes;
    int dia;
    String fechaFoto;
    Button bViewElegirFecha, botonRestarFecha, botonSumarFecha;
    ImageView imgViewVerFotos;
    PermisosDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectorTemas.aplicarTema(this);
        setContentView(R.layout.activity_permisos_fotos);
        getSupportActionBar().hide();

        db = new PermisosDatabase(this);

        bViewElegirFecha = findViewById(R.id.bPermFotElegirFecha);
        bViewElegirFecha.setOnClickListener(this);

        imgViewVerFotos = findViewById(R.id.imgPermFotImagen);

        botonRestarFecha = findViewById(R.id.bPermFotRestarFecha);
        botonRestarFecha.setOnClickListener(this);
        botonSumarFecha = findViewById(R.id.bPermFotSumarFecha);
        botonSumarFecha.setOnClickListener(this);

        Calendar calendario = Calendar.getInstance();
        year = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);

        String textoFecha = dia + "/" + (mes + 1) + "/" + year;
        bViewElegirFecha.setText(textoFecha);

        elegirFotoPorFechaSelec();
    }

    private void elegirFotoPorFechaSelec() {
        Bitmap fotoDelDia = db.getFotoDelDia(bViewElegirFecha.getText().toString());
        if (fotoDelDia != null) {
            imgViewVerFotos.setImageBitmap(fotoDelDia);
        } else {
            imgViewVerFotos.setImageDrawable(getResources().getDrawable(R.drawable.permisos_meme));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bPermFotElegirFecha:
                DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        fechaFoto = dayOfMonth + "/" + month + "/" + year;

                        // Actualiza las variables globales
                        PermisosFotosActivity.this.year = year;
                        PermisosFotosActivity.this.mes = month - 1;
                        PermisosFotosActivity.this.dia = dayOfMonth;

                        bViewElegirFecha.setText(fechaFoto);

                        if (fechaFoto.equals("12/2/2013")) {
                            mediaPlayer = MediaPlayer.create(PermisosFotosActivity.this,R.raw.audio);
                            mediaPlayer.setLooping(true);
                            ViewGroup.LayoutParams params = imgViewVerFotos.getLayoutParams();
                            float density = getResources().getDisplayMetrics().density;
                            params.height = (int) (340 * density);
                            imgViewVerFotos.setLayoutParams(params);
                            imgViewVerFotos.setImageDrawable(getResources().getDrawable(R.drawable.permisos_imagen));
                            if (!mediaPlayer.isPlaying()) {
                                mediaPlayer.start();
                            }
                        } else {
                            ViewGroup.LayoutParams params = imgViewVerFotos.getLayoutParams();
                            float density = getResources().getDisplayMetrics().density;
                            params.height = (int) (520 * density);
                            imgViewVerFotos.setLayoutParams(params);
                            elegirFotoPorFechaSelec();
                        }
                    }
                }, year, mes, dia);
                dialog.show();

                break;
            case R.id.bPermFotRestarFecha:
                String[] partesFecha = bViewElegirFecha.getText().toString().split("/");
                Calendar calendario = Calendar.getInstance();
                calendario.set(Integer.parseInt(partesFecha[2]), Integer.parseInt(partesFecha[1]) - 1, Integer.parseInt(partesFecha[0]));
                calendario.add(Calendar.DAY_OF_MONTH, -1);
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH) + 1;
                year = calendario.get(Calendar.YEAR);
                fechaFoto = dia + "/" + mes + "/" + year;
                bViewElegirFecha.setText(fechaFoto);
                elegirFotoPorFechaSelec();
                break;
            case R.id.bPermFotSumarFecha:
                partesFecha = bViewElegirFecha.getText().toString().split("/");
                calendario = Calendar.getInstance();
                calendario.set(Integer.parseInt(partesFecha[2]), Integer.parseInt(partesFecha[1]) - 1, Integer.parseInt(partesFecha[0]));
                calendario.add(Calendar.DAY_OF_MONTH, 1);
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH) + 1;
                year = calendario.get(Calendar.YEAR);
                fechaFoto = dia + "/" + mes + "/" + year;
                bViewElegirFecha.setText(fechaFoto);
                elegirFotoPorFechaSelec();
                break;
        }
    }
}
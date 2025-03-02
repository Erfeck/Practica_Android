package com.example.util_idades.api;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.util_idades.R;
import com.example.util_idades.SelectorTemas;

public class ApiDataActivity extends AppCompatActivity {

    PersonajeDB unPersonaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectorTemas.aplicarTema(this);
        setContentView(R.layout.activity_api_data);
        getSupportActionBar().hide();

        unPersonaje = (PersonajeDB) getIntent().getSerializableExtra("personaje");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApiTarea unaTarea = new ApiTarea(this,unPersonaje);
        unaTarea.execute(ApiActivity.MI_URL);
    }

    public void actualizarInterfaz(PersonajeDB personaje) {
        TextView tvNombre = findViewById(R.id.tvApiNombre);
        TextView tvKi = findViewById(R.id.tvApiKi);
        TextView tvMaxKi = findViewById(R.id.tvApiMaxKi);
        TextView tvRaza = findViewById(R.id.tvApiRaza);
        TextView tvGenero = findViewById(R.id.tvApiGenero);
        TextView tvEquipo = findViewById(R.id.tvApiEquipo);
        TextView tvDescripcion = findViewById(R.id.tvApiDescripcion);
        ImageView ivImagen = findViewById(R.id.ivApiImagen);

        tvNombre.setText(personaje.getNombre());

        String textoKi = "KI: " + personaje.getKi();
        tvKi.setText(textoKi);

        String textoMaxKi = "KI Máximo: " + personaje.getMaxKi();
        tvMaxKi.setText(textoMaxKi);

        String textoRaza = "Raza: " + personaje.getRaza();
        tvRaza.setText(textoRaza);

        String textoGenero = "Género: " + personaje.getGenero();
        tvGenero.setText(textoGenero);

        String textoEquipo = "Equipo: " + personaje.getEquipo();
        tvEquipo.setText(textoEquipo);

        String textoDescripcion = "Descripción: " + personaje.getDescripcion();
        tvDescripcion.setText(textoDescripcion);

        Glide.with(this).load(personaje.getImage()).into(ivImagen);
    }
}
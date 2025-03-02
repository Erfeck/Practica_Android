package com.example.util_idades.api;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.util_idades.R;
import com.example.util_idades.SelectorTemas;

import java.io.Serializable;
import java.util.ArrayList;

public class ApiActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    static String MI_URL = "https://dragonball-api.com/api/characters?page=1";
    ListView lvApiLista;
    static ArrayList<PersonajeDB> listaPersonajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectorTemas.aplicarTema(this);
        setContentView(R.layout.activity_api);
        getSupportActionBar().hide();

        lvApiLista = findViewById(R.id.lvApiLista);
        lvApiLista.setOnItemClickListener(this);

        listaPersonajes = new ArrayList<>();

        ApiTarea unaTarea = new ApiTarea(this);
        unaTarea.execute(MI_URL);
    }

    public void pintarMonumentos() {
        AdapterPersonaje adapter = new AdapterPersonaje(ApiActivity.this, listaPersonajes);
        lvApiLista.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PersonajeDB personajeSeleccionado = listaPersonajes.get(position);

        Intent intent = new Intent(this, ApiDataActivity.class);
        intent.putExtra("personaje", (Serializable) personajeSeleccionado);
        startActivity(intent);
    }
}
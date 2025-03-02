package com.example.util_idades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.example.util_idades.api.ApiActivity;
import com.example.util_idades.calculadora.CalculadoraActivity;
import com.example.util_idades.conecta4.Conecta4Activity;
import com.example.util_idades.divisas.DivisasActivity;
import com.example.util_idades.listacompra.CompraActivity;
import com.example.util_idades.permisos.PermisosActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgCalculadora, imgConversor, imgListaCompra;
    ImageView imgAPI, imgPermisos, imgConecta4;
    ImageView imgSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectorTemas.aplicarTema(this);
        elegirModoApp();
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        imgCalculadora = findViewById(R.id.imgHomeCalc);
        imgCalculadora.setImageDrawable(getResources().getDrawable(R.drawable.home_calc));
        imgCalculadora.setOnClickListener(this);

        imgConversor = findViewById(R.id.imgHomeConversor);
        imgConversor.setImageDrawable(getResources().getDrawable(R.drawable.home_divisas));
        imgConversor.setOnClickListener(this);

        imgListaCompra = findViewById(R.id.imgHomeLista);
        imgListaCompra.setImageDrawable(getResources().getDrawable(R.drawable.home_listacompra));
        imgListaCompra.setOnClickListener(this);

        imgAPI = findViewById(R.id.imgHomeAPI);
        imgAPI.setImageDrawable(getResources().getDrawable(R.drawable.home_dragonball));
        imgAPI.setOnClickListener(this);

        imgPermisos = findViewById(R.id.imgHomePermisos);
        imgPermisos.setImageDrawable(getResources().getDrawable(R.drawable.home_permisoscamara));
        imgPermisos.setOnClickListener(this);

        imgConecta4 = findViewById(R.id.imgHomeConecta4);
        imgConecta4.setImageDrawable(getResources().getDrawable(R.drawable.home_conecta4));
        imgConecta4.setOnClickListener(this);

        imgSettings = findViewById(R.id.imgHomeSettings);
        imgSettings.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.imgHomeCalc:
                intent = new Intent(this, CalculadoraActivity.class);
                startActivity(intent);
                break;
            case R.id.imgHomeConversor:
                intent = new Intent(this, DivisasActivity.class);
                startActivity(intent);
                break;
            case R.id.imgHomeLista:
                intent = new Intent(this, CompraActivity.class);
                startActivity(intent);
                break;
            case R.id.imgHomeAPI:
                intent = new Intent(this, ApiActivity.class);
                startActivity(intent);
                break;
            case R.id.imgHomePermisos:
                intent = new Intent(this, PermisosActivity.class);
                startActivity(intent);
                break;
            case R.id.imgHomeConecta4:
                intent = new Intent(this, Conecta4Activity.class);
                startActivity(intent);
                break;
            case R.id.imgHomeSettings:
                intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void elegirModoApp() {
        boolean quiereModoOscuro = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getBoolean("modo_oscuro",true);
        // Aplicar el modo correspondiente
        if (quiereModoOscuro) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
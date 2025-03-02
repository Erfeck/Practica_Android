package com.example.util_idades.listacompra;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.util_idades.R;
import com.example.util_idades.SelectorTemas;
import com.example.util_idades.listacompra.adapter.CompraSpinnerAdapter;
import com.example.util_idades.listacompra.bd.CompraDatabase;
import com.example.util_idades.listacompra.productos.Categoria;
import com.example.util_idades.listacompra.productos.ElementoCompra;
import com.example.util_idades.util.Util;

import java.util.List;

public class CompraAddElemActivity extends AppCompatActivity implements View.OnClickListener {

    private CompraDatabase db;
    EditText etxtAddElemNombre;
    Button bAddElemAgregar, bAddElemAgregarCtg;
    Spinner spnAddCtg;
    List<Categoria> listaCategorias;

    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectorTemas.aplicarTema(this);
        setContentView(R.layout.activity_compra_add_elem);
        getSupportActionBar().hide();

        db = new CompraDatabase(this);

        etxtAddElemNombre = findViewById(R.id.etxtCompraElemNombre);

        bAddElemAgregar = findViewById(R.id.bCompraElemAgregarElem);
        bAddElemAgregar.setOnClickListener(this);

        bAddElemAgregarCtg = findViewById(R.id.bCompraElemAgregarCtg);
        bAddElemAgregarCtg.setOnClickListener(this);

        spnAddCtg = findViewById(R.id.spnCompraElemCategoria);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaCategorias = db.getCategorias();
        String[] nombresCategorias = new String[listaCategorias.size()];
        for (int i = 0; i < listaCategorias.size(); i++) {
            nombresCategorias[i] = Util.capitalizarCadaPalabra(listaCategorias.get(i).getNombre());
        }

        CompraSpinnerAdapter adapter = new CompraSpinnerAdapter(this,nombresCategorias,db.getCategorias());
        spnAddCtg.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bCompraElemAgregarElem:
                String nombreElemento = etxtAddElemNombre.getText().toString();
                String nombreCategoriaSelec = spnAddCtg.getSelectedItem().toString();
                int idCategoriaSelec = recuperarIdSeleccionado(nombreCategoriaSelec);

                if (!nombreElemento.isEmpty()) {
                    nombreElemento = nombreElemento.trim();
                    if (!db.existeNombre(nombreElemento)) {
                        ElementoCompra unElemento = new ElementoCompra(nombreElemento, false, idCategoriaSelec);
                        db.nuevoElementoCompra(unElemento, unElemento.getIdCategoria());
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "El nombre ya existe", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No puedes dejar el nombre vacío", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bCompraElemAgregarCtg:
                Intent intent = new Intent(this, CompraAddCtgActivity.class);
                startActivity(intent);
                break;
        }
    }


    private int recuperarIdSeleccionado(String nombreCategoriaSelec) {
        nombreCategoriaSelec = nombreCategoriaSelec.toLowerCase();

        int idCategoriaSelec = -1;
        for (Categoria unaCategoria : listaCategorias) {
            if (nombreCategoriaSelec.equals(unaCategoria.getNombre())) {
                idCategoriaSelec = unaCategoria.getId();
            }
        }
        return idCategoriaSelec;
    }
}
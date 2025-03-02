package com.example.util_idades.listacompra;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.util_idades.R;
import com.example.util_idades.SelectorTemas;
import com.example.util_idades.listacompra.adapter.CompraAdapter;
import com.example.util_idades.listacompra.bd.CompraDatabase;
import com.example.util_idades.listacompra.productos.Categoria;
import com.example.util_idades.listacompra.productos.ElementoCompra;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CompraActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerLista;
    private FloatingActionButton fbaAgregar;
    private List<ElementoCompra> listaCompra;
    private CompraAdapter adaptadorLista;
    private CompraDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectorTemas.aplicarTema(this);
        setContentView(R.layout.activity_compra);
        getSupportActionBar().hide();

        db = new CompraDatabase(this);

        crearCategoriasIniciales();

        recyclerLista = findViewById(R.id.recyclerListaCompra);

        extraerElementosDB();

        fbaAgregar = findViewById(R.id.fabCompraAgregar);
        fbaAgregar.setOnClickListener(this);
    }

    private void crearCategoriasIniciales() {
        if (db.getCategorias().isEmpty()) {
            Categoria unaCategoria = crearCategoriaInicial("Panadería",R.drawable.compra_pan);
            db.nuevaCategoria(unaCategoria);

            unaCategoria = crearCategoriaInicial("Lácteos",R.drawable.compra_lacteos);
            db.nuevaCategoria(unaCategoria);

            unaCategoria = crearCategoriaInicial("Carnes",R.drawable.compra_carne);
            db.nuevaCategoria(unaCategoria);

            unaCategoria = crearCategoriaInicial("Pescados",R.drawable.compra_pescado);
            db.nuevaCategoria(unaCategoria);
        }

    }

    @NonNull
    private Categoria crearCategoriaInicial(String nombreCategoria, int idImagen) {
        ImageView imagen = new ImageView(this);

        imagen.setImageDrawable(getResources().getDrawable(idImagen));

        Categoria unaCategoria = new Categoria( nombreCategoria,((BitmapDrawable) imagen.getDrawable()).getBitmap());
        return unaCategoria;
    }

    private void extraerElementosDB() {
        listaCompra = db.getElementosCompra();

        adaptadorLista = new CompraAdapter(this, db);
        recyclerLista.setLayoutManager(new LinearLayoutManager(this));
        recyclerLista.setAdapter(adaptadorLista);
    }

    @Override
    protected void onResume() {
        super.onResume();
        extraerElementosDB();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CompraAddElemActivity.class);
        startActivity(intent);
    }
}
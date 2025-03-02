package com.example.util_idades.listacompra.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.util_idades.R;
import com.example.util_idades.listacompra.bd.CompraDatabase;
import com.example.util_idades.listacompra.productos.Categoria;
import com.example.util_idades.listacompra.productos.ElementoCompra;
import com.example.util_idades.util.Util;

import java.util.List;

public class CompraAdapter extends RecyclerView.Adapter<CompraViewHolder> {

    private List<ElementoCompra> listaElementosCompra;
    private List<Categoria> listaCategorias;
    private Context context;
    private CompraDatabase db;

    public CompraAdapter(Context context, CompraDatabase db) {
        this.context = context;
        this.db = db;
        this.listaElementosCompra = db.getElementosCompra();
        this.listaCategorias = db.getCategorias();
    }

    @NonNull
    @Override
    public CompraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.compra_elemento, parent, false);
        return new CompraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraViewHolder holder, int position) {
        ElementoCompra unElemento = listaElementosCompra.get(position);
        Categoria unaCategoria = null;
        for (Categoria categoria : listaCategorias) {
            if (categoria.getId() == unElemento.getIdCategoria()) {
                unaCategoria = categoria;
                break;
            }
        }

        if (unaCategoria!= null) {
            // La categoría se encuentra en la lista de categorías
            Bitmap bitmap = unaCategoria.getFoto(); // suponiendo que getFoto() devuelve un bitmap
            BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
            holder.imgElementoCategoria.setImageDrawable(bitmapDrawable);
            holder.nombreElementoCategoria.setText(Util.capitalizarCadaPalabra(unaCategoria.getNombre()));
        } else {
            // La categoría no se encuentra en la lista de categorías
            // Puedes mostrar un mensaje de error o un placeholder
            holder.imgElementoCategoria.setImageResource(R.drawable.compra_placeholder);
            holder.nombreElementoCategoria.setText("Categoría no encontrada");
        }

        holder.cbElementoSeleccionar.setText(Util.capitalizarCadaPalabra(unElemento.getTexto()));
        holder.cbElementoSeleccionar.setChecked(false);

        holder.cbElementoSeleccionar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                eliminarElemento(holder.getAdapterPosition());
            }
        });
    }

    public void eliminarElemento(int position) {
        ElementoCompra unElemento = listaElementosCompra.get(position);
        for (ElementoCompra elementoCompra : listaElementosCompra) {
            System.out.println(elementoCompra.getTexto() + " - " + listaElementosCompra.indexOf(elementoCompra));
        }
        System.out.println("Eliminando elemento posición: " + position);
        db.eliminarElemento(unElemento.getTexto());
        notifyItemRemoved(position);
        listaElementosCompra = db.getElementosCompra();
    }

    @Override
    public int getItemCount() {
        return listaElementosCompra.size();
    }
}
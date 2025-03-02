package com.example.util_idades.listacompra.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.util_idades.R;
import com.example.util_idades.listacompra.productos.Categoria;

import java.util.List;

public class CompraSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] elementos;
    private List<Categoria> listaCategorias;

    public CompraSpinnerAdapter(@NonNull Context context, String[] elementos, List<Categoria> listaCategorias) {
        super(context, R.layout.divisas_spinner, elementos);
        this.context = context;
        this.elementos = elementos;
        this.listaCategorias = listaCategorias;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.compra_spinner, parent, false);

        TextView textoCtg = view.findViewById(R.id.txtSpnCompraCtg);
        ImageView imgCtg = view.findViewById(R.id.imgSpnCompraCtg);

        textoCtg.setText(elementos[position]);
        imgCtg.setImageBitmap(elegirImagen(textoCtg));

        return view;
    }

    private Bitmap elegirImagen(TextView textoCtg) {
        String nombreCategoria = textoCtg.getText().toString().toLowerCase();

        for (Categoria unaCategoria : listaCategorias) {
            if (unaCategoria.getNombre().equals(nombreCategoria)) {
                return unaCategoria.getFoto();
            }
        }
        return BitmapFactory.decodeResource(context.getResources(),R.drawable.compra_placeholder);
    }
}
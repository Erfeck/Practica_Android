package com.example.util_idades.listacompra.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.util_idades.R;


public class CompraViewHolder extends RecyclerView.ViewHolder {

    ImageView imgElementoCategoria;
    TextView nombreElementoCategoria;
    CheckBox cbElementoSeleccionar;

    public CompraViewHolder(@NonNull View itemView) {
        super(itemView);
        imgElementoCategoria = itemView.findViewById(R.id.imgCompraElementoCategoria);
        nombreElementoCategoria = itemView.findViewById(R.id.tvCompraElementoCategoria);
        cbElementoSeleccionar = itemView.findViewById(R.id.cbCompraElementoSeleccionar);
    }
}
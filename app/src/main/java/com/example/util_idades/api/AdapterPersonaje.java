package com.example.util_idades.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.util_idades.R;

import java.util.ArrayList;

public class AdapterPersonaje extends BaseAdapter {

    private Context context;
    private ArrayList<PersonajeDB> listaPersonajes;

    public AdapterPersonaje(Context context, ArrayList<PersonajeDB> listaPersonajes) {
        this.context = context;
        this.listaPersonajes = listaPersonajes;
    }

    @Override
    public int getCount() {
        return listaPersonajes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPersonajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;
        if (vista == null) {
            vista = LayoutInflater.from(context).inflate(R.layout.api_personaje,parent,false);
        }

        PersonajeDB unPersonaje = (PersonajeDB) getItem(position);

        TextView tvNombre = vista.findViewById(R.id.tvApiNombre);
        TextView tvKi = vista.findViewById(R.id.tvApiKi);
        TextView tvMaxKi = vista.findViewById(R.id.tvApiMaxKi);
        TextView tvRaza = vista.findViewById(R.id.tvApiRaza);
        TextView tvGenero = vista.findViewById(R.id.tvApiGenero);
        TextView tvEquipo = vista.findViewById(R.id.tvApiEquipo);
        TextView tvDescripcion = vista.findViewById(R.id.tvApiDescripcion);
        ImageView ivImagen = vista.findViewById(R.id.ivApiImagen);
        Glide.with(context).load(unPersonaje.getImage()).into(ivImagen);

        tvNombre.setText(unPersonaje.getNombre());
        tvKi.setText(unPersonaje.getKi());
        tvMaxKi.setText(unPersonaje.getMaxKi());
        tvRaza.setText(unPersonaje.getRaza());
        tvGenero.setText(unPersonaje.getGenero());
        tvEquipo.setText(unPersonaje.getEquipo());
        tvDescripcion.setText(unPersonaje.getDescripcion());

        tvKi.setVisibility(View.GONE);
        tvMaxKi.setVisibility(View.GONE);
        tvRaza.setVisibility(View.GONE);
        tvGenero.setVisibility(View.GONE);
        tvEquipo.setVisibility(View.GONE);
        tvDescripcion.setVisibility(View.GONE);

        return vista;
    }
}

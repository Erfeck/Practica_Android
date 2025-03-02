package com.example.util_idades.divisas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.util_idades.R;

import java.util.ArrayList;

public class DivisaSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> elementos;

    public DivisaSpinnerAdapter(@NonNull Context context, ArrayList<String> elementos) {
        super(context, R.layout.divisas_spinner, elementos);
        this.context = context;
        this.elementos = elementos;
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
        View view = LayoutInflater.from(context).inflate(R.layout.divisas_spinner, parent, false);

        TextView textoDivisa = view.findViewById(R.id.txtSpnDivisas);
        ImageView imgDivisa = view.findViewById(R.id.imgSpnDivisas);

        textoDivisa.setText(elementos.get(position));
        imgDivisa.setImageResource(elegirImagen(textoDivisa));
        //icon.setImageResource(R.drawable.ic_launcher_foreground);

        return view;
    }

    private int elegirImagen(TextView textoDivisa) {
        String nombreDivisa = textoDivisa.getText().toString();

        switch (nombreDivisa) {
            case "EUR":
                return R.drawable.divisas_europa;
            case "USD":
                return R.drawable.divisas_eeuu;
            case "GBP":
                return R.drawable.divisas_uk;
            case "JPY":
                return R.drawable.divisas_japon;
            case "COP":
                return R.drawable.divisas_colombia;
            case "VES":
                return R.drawable.divisas_venezuela;

        }
        return R.drawable.divisas_espana;
    }
}
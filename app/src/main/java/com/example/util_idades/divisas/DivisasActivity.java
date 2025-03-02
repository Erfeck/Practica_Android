package com.example.util_idades.divisas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.util_idades.R;
import com.example.util_idades.SelectorTemas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DivisasActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etxtCantidad;
    Spinner spnDivisaInicial, spnDivisaFinal;
    TextView txtResultado;
    Button botonCalcular, botonIntercambiar;
    CardView cuadroTexto;

    static HashMap<String, Double> mapDivisas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectorTemas.aplicarTema(this);
        setContentView(R.layout.activity_divisas);
        getSupportActionBar().hide();

        etxtCantidad = findViewById(R.id.etxtMainCantidad);
        spnDivisaInicial = findViewById(R.id.spnMainDivisaInicial);
        spnDivisaFinal = findViewById(R.id.spnMainDivisalFinal);
        txtResultado = findViewById(R.id.txtDivisasResultado);
        botonCalcular = findViewById(R.id.bDivisasCalcular);
        botonIntercambiar = findViewById(R.id.bDivisasIntercambiar);
        cuadroTexto = findViewById(R.id.cardDivisasCuadro);

        cuadroTexto.setVisibility(View.GONE);

        botonCalcular.setOnClickListener(this);
        botonIntercambiar.setOnClickListener(this);

        mapDivisas = new HashMap<String, Double>();
        mapDivisas.put("EUR",1.0);
        mapDivisas.put("USD",1.05);
        mapDivisas.put("GBP",0.83);
        mapDivisas.put("JPY",164.08);
        mapDivisas.put("COP",4716.0);
        mapDivisas.put("VES",47.47);

        ArrayList<String> listaDivisas = new ArrayList<>(mapDivisas.keySet());
        DivisaSpinnerAdapter adapter = new DivisaSpinnerAdapter(this,listaDivisas);
        spnDivisaInicial.setAdapter(adapter);
        spnDivisaFinal.setAdapter(adapter);

        spnDivisaInicial.setSelection(1);
        spnDivisaFinal.setSelection(5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bDivisasIntercambiar:
                int posicionSpnInicial = spnDivisaInicial.getSelectedItemPosition();
                int posicionSpnFinal = spnDivisaFinal.getSelectedItemPosition();

                spnDivisaInicial.setSelection(posicionSpnFinal);
                spnDivisaFinal.setSelection(posicionSpnInicial);
                break;
            case R.id.bDivisasCalcular:
                String txtCantidad = etxtCantidad.getText().toString();

                if (txtCantidad.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Debes poner una cantidad",Toast.LENGTH_LONG).show();
                    return;
                }
                if (txtCantidad.equals(".")) {
                    Toast.makeText(getApplicationContext(),"No se puede poner solo un punto",Toast.LENGTH_LONG).show();
                    cuadroTexto.setVisibility(View.GONE);
                    txtResultado.setText(null);
                    return;
                }
                double cantidad = Double.parseDouble(txtCantidad);

                double divisa1 = obtenerConversion(spnDivisaInicial.getSelectedItem().toString());
                double divisa2 = obtenerConversion(spnDivisaFinal.getSelectedItem().toString());

                if (divisa1 == divisa2) {
                    Toast.makeText(getApplicationContext(),"Espabila",Toast.LENGTH_LONG).show();
                    return;
                }

                double resultado = cantidad * (divisa2/divisa1);
                cuadroTexto.setVisibility(View.VISIBLE);

                String textoDivisa = "Cambio: " +
                        String.format("%.2f",resultado) +
                        " " +
                        elegirSimbolo(spnDivisaFinal.getSelectedItem().toString());
                txtResultado.setText(textoDivisa);
                break;
        }
    }

    private String elegirSimbolo(String divisa) {
        switch (divisa) {
            case "EUR":
                return "€";
            case "USD":
            case "COP":
                return "$";
            case "GBP":
                return "£";
            case "JPY":
                return "¥";
            case "VES":
                return "Bs.";
        }
        return "Error";
    }

    private double obtenerConversion(String divisaIntroducida) {
        for (Map.Entry<String, Double> entrada  : mapDivisas.entrySet()) {
            String unaDivisa = entrada.getKey();
            double valor = entrada.getValue();
            if (divisaIntroducida.equals(unaDivisa)) {
                return valor;
            }
        }
        return 0;
    }
}
package com.example.util_idades.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.util_idades.R;
import com.example.util_idades.SelectorTemas;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtResultado;
    Button boton0, boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8, boton9;
    Button botonResultado, botonSumar, botonRestar, botonMultiplicar, botonDividir;
    Button botonComa, botonDEL, botonCE;
    private boolean realizoOperacion;
    private boolean realizoOperacionPorOperador;
    private boolean dividioPorCero;
    private boolean numeroDemasiadoLargo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectorTemas.aplicarTemaCalc(this);
        setContentView(R.layout.activity_calculadora);
        getSupportActionBar().hide();

        realizoOperacion = false;
        realizoOperacionPorOperador = false;
        dividioPorCero = false;
        numeroDemasiadoLargo = false;

        txtResultado = findViewById(R.id.txtCalcOperacion);

        boton0 = findViewById(R.id.bCalc0);
        boton0.setOnClickListener(this);

        boton1 = findViewById(R.id.bCalc1);
        boton1.setOnClickListener(this);

        boton2 = findViewById(R.id.bCalc2);
        boton2.setOnClickListener(this);

        boton3 = findViewById(R.id.bCalc3);
        boton3.setOnClickListener(this);

        boton4 = findViewById(R.id.bCalc4);
        boton4.setOnClickListener(this);

        boton5 = findViewById(R.id.bCalc5);
        boton5.setOnClickListener(this);

        boton6 = findViewById(R.id.bCalc6);
        boton6.setOnClickListener(this);

        boton7 = findViewById(R.id.bCalc7);
        boton7.setOnClickListener(this);

        boton8 = findViewById(R.id.bCalc8);
        boton8.setOnClickListener(this);

        boton9 = findViewById(R.id.bCalc9);
        boton9.setOnClickListener(this);

        botonResultado = findViewById(R.id.bCalcResultado);
        botonResultado.setOnClickListener(this);

        botonSumar = findViewById(R.id.bCalcSuma);
        botonSumar.setOnClickListener(this);

        botonRestar = findViewById(R.id.bCalcResta);
        botonRestar.setOnClickListener(this);

        botonMultiplicar = findViewById(R.id.bCalcMultiplicar);
        botonMultiplicar.setOnClickListener(this);

        botonDividir = findViewById(R.id.bCalcDividir);
        botonDividir.setOnClickListener(this);

        botonComa = findViewById(R.id.bCalcComa);
        botonComa.setOnClickListener(this);

        botonDEL = findViewById(R.id.bCalcDEL);
        botonDEL.setOnClickListener(this);
        botonCE = findViewById(R.id.bCalcCE);
        botonCE.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (numeroDemasiadoLargo) {
            numeroDemasiadoLargo = false;
            Toast.makeText(
                    this,
                    "El número es demasiado largo para manipularlo. Se va a reiniciar el resultado.",
                    Toast.LENGTH_SHORT).show();
            txtResultado.setText("0");
            return;
        }
        switch (v.getId()) {
            case R.id.bCalcResultado:
                txtResultado.setText(realizarOperacion(txtResultado.getText().toString()));
                break;
            case R.id.bCalc0:
                txtResultado.setText(colocarNumero("0"));
                break;
            case R.id.bCalc1:
                txtResultado.setText(colocarNumero("1"));
                break;
            case R.id.bCalc2:
                txtResultado.setText(colocarNumero("2"));
                break;
            case R.id.bCalc3:
                txtResultado.setText(colocarNumero("3"));
                break;
            case R.id.bCalc4:
                txtResultado.setText(colocarNumero("4"));
                break;
            case R.id.bCalc5:
                txtResultado.setText(colocarNumero("5"));
                break;
            case R.id.bCalc6:
                txtResultado.setText(colocarNumero("6"));
                break;
            case R.id.bCalc7:
                txtResultado.setText(colocarNumero("7"));
                break;
            case R.id.bCalc8:
                txtResultado.setText(colocarNumero("8"));
                break;
            case R.id.bCalc9:
                txtResultado.setText(colocarNumero("9"));
                break;
            case R.id.bCalcSuma:
                txtResultado.setText(colocarOperador("+"));
                break;
            case R.id.bCalcResta:
                txtResultado.setText(colocarOperador("-"));
                break;
            case R.id.bCalcMultiplicar:
                txtResultado.setText(colocarOperador("x"));
                break;
            case R.id.bCalcDividir:
                txtResultado.setText(colocarOperador("÷"));
                break;
            case R.id.bCalcCE:
                realizoOperacion = false;
                realizoOperacionPorOperador = false;
                if (dividioPorCero) {
                    dividioPorCero = false;
                    txtResultado.setTextSize(48);
                }
                txtResultado.setText("0");
                break;
            case R.id.bCalcDEL:
                borrarUltimoCaracter(txtResultado.getText().toString());
                break;
            case R.id.bCalcComa:
                txtResultado.setText(colocarComa(txtResultado.getText().toString()));
                break;
        }
    }

    private String realizarOperacion(String operacion) {
        if (operacion.equals("0") || operacion.equals("0,")) {
            return "0";
        }
        if (operacion.startsWith("-")) {
            if (!contieneOperador(operacion.substring(1))) {
                return operacion;
            }
        }
        if (!contieneOperador(operacion)) {
            return operacion;
        }
        if (acabaConOperador(operacion)) {
            operacion = operacion.substring(0, operacion.length() - 1);
            if (operacion.endsWith(",")) {
                return operacion.substring(0, operacion.length() - 1);
            }
            return operacion;
        }


        String textoPrimerNumero = "";
        BigDecimal primerNumero = BigDecimal.ZERO;
        String operador = "";
        String textoSegundoNumero = "";
        BigDecimal segundoNumero = new BigDecimal("0");

        boolean esNegativo = false;
        //Empieza en 0, es negativo -> Ej: 0-12+10
        if (operacion.startsWith("-")) {
            operacion = operacion.substring(1);
            esNegativo = true;
        }

        String[] numerosOperacion = operacion.split("[+\\-x÷]");
        textoPrimerNumero = numerosOperacion[0];
        if (esNegativo) {
            textoPrimerNumero = "-" + textoPrimerNumero;
        }
        textoSegundoNumero = numerosOperacion[1];
        operador = operacion.substring(numerosOperacion[0].length(), numerosOperacion[0].length() + 1);

        if (revisarDivisionPartido0(textoSegundoNumero, operador)) {
            dividioPorCero = true;
            txtResultado.setTextSize(34);
            return "No se puede dividir por 0";
        }

        primerNumero = new BigDecimal(textoPrimerNumero.replace(".", "").replace(",", "."));
        segundoNumero = new BigDecimal(textoSegundoNumero.replace(".", "").replace(",", "."));

        BigDecimal resultadoOperacion = BigDecimal.ZERO;
        switch (operador) {
            case "+":
                resultadoOperacion = primerNumero.add(segundoNumero);
                break;
            case "-":
                resultadoOperacion = primerNumero.subtract(segundoNumero);
                break;
            case "x":
                resultadoOperacion = primerNumero.multiply(segundoNumero);
                break;
            case "÷":
                resultadoOperacion = primerNumero.divide(segundoNumero,33, RoundingMode.HALF_UP).stripTrailingZeros();
                break;
        }

        realizoOperacion = true;
        String resultadoFinal = null;
        resultadoOperacion = resultadoOperacion.stripTrailingZeros();
        int prueba = resultadoOperacion.scale();
        if (resultadoOperacion.scale() == 0) {
            resultadoFinal = resultadoOperacion.toBigInteger().toString();
        } else {
            resultadoFinal = resultadoOperacion.toPlainString();
        }
        resultadoFinal = resultadoFinal.replace(".", ",");
        resultadoFinal = colocarPunto(resultadoFinal);
        if (superoCantidadNumerosMax(resultadoFinal)) {
            numeroDemasiadoLargo =  true;
        }
        return resultadoFinal;
    }

    private static boolean revisarDivisionPartido0(String segundoNumero, String operador) {
        segundoNumero = segundoNumero.replace(".","");
        segundoNumero = segundoNumero.replace(",",".");
        double divisor = Double.parseDouble(segundoNumero);
        return divisor == 0 && operador.equals("÷");
    }

    public String colocarNumero(String numeroAIntroducir) {
        String operacion = txtResultado.getText().toString();

        if (realizoOperacionPorOperador) {
            realizoOperacion = false;
            realizoOperacionPorOperador = false;
            return operacion + numeroAIntroducir;
        }
        if (realizoOperacion) {
            realizoOperacion = false;
            return numeroAIntroducir;
        }
        if (superoCantidadNumerosMax(operacion)) {
            return operacion;
        }

        String operacionActualizada = "";
        if (operacion.equals("0")) {
            return numeroAIntroducir;
        } else if (operacion.equals("-")) {
            return operacion + numeroAIntroducir;
        } else {
            operacionActualizada = operacion + numeroAIntroducir;
        }

        boolean primerNumeroNegativo = operacionActualizada.startsWith("-");
        if (primerNumeroNegativo) {
            operacionActualizada = operacionActualizada.substring(1);
        }

        if (contieneOperador(operacionActualizada)) {
            String[] partesDeLaOperacion = operacionActualizada.split("[+\\-x÷]");

            String ultimoNumero = partesDeLaOperacion[1];

            //EVITAR QUE AÑADA MULTIPLOS CEROS DESPUÉS DE AÑADIR OPERADOR.
            if (ultimoNumero.equals("00") && numeroAIntroducir.equals("0")) {
                return operacion;
            }

            //Esta parte ya debe tener su ColocarPunto actualizado -> partesDeLaOperacion[0]
            String operacionSinUltimoNumero = operacionActualizada.substring(0, operacionActualizada.length() - ultimoNumero.length());

            String numeroActualizado = colocarPunto(ultimoNumero.replace(".", ""));

            if (primerNumeroNegativo) {
                return "-" + operacionSinUltimoNumero + numeroActualizado;
            } else {
                return operacionSinUltimoNumero + numeroActualizado;
            }
        } else {
            String numeroActualizado = colocarPunto(operacionActualizada.replace(".", ""));
            if (primerNumeroNegativo) {
                return "-" + numeroActualizado;
            } else {
                return numeroActualizado;
            }
        }
    }

    private boolean superoCantidadNumerosMax(String operacion) {
        if (operacion.startsWith("-")) {
            operacion = operacion.substring(1);
        }

        String ultimoNumero = "";
        if (contieneOperador(operacion) && !acabaConOperador(operacion)) {
            String[] partesOperacion = operacion.split("[+\\-x÷]");

            ultimoNumero = partesOperacion[1];
        } else if (acabaConOperador(operacion)) {
            return false;
        } else {
            ultimoNumero = operacion;
        }

        ultimoNumero = ultimoNumero.replace(".","");
        ultimoNumero = ultimoNumero.replace(",","");

        return ultimoNumero.length() > 15;
    }

    private static String colocarPunto(String numeroIntroducido) {
        boolean tieneComaAlFinal = false;
        String parteEntera = numeroIntroducido;
        String parteDecimal = null;

        if (numeroIntroducido.contains(",")) {
            if (numeroIntroducido.endsWith(",")) {
                tieneComaAlFinal = true;
                parteEntera = numeroIntroducido.substring(0, numeroIntroducido.length() - 1);
            } else {
                String[] numeroConDecimal = numeroIntroducido.split(",");
                parteEntera = numeroConDecimal[0];
                parteDecimal = numeroConDecimal[1];
            }

        }

        int contador = 0;
        String nuevaParteEntera = "";
        for (int i = parteEntera.length() - 1; i >= 0; i--) {
            if (contador % 3 == 0 && contador != 0) {
                nuevaParteEntera = "." + nuevaParteEntera;
            }
            //Agregamos número a número en cada iteración.
            nuevaParteEntera = numeroIntroducido.charAt(i) + nuevaParteEntera;
            contador++;
        }

        //Da error porque igual el número no tiene decimal.
        if (parteDecimal == null) {
            if (tieneComaAlFinal) {
                return nuevaParteEntera + ",";
            }
            return nuevaParteEntera;
        } else {
            return nuevaParteEntera + "," + parteDecimal;
        }
    }

    private static boolean contieneOperador(String operacion) {
        if (operacion.contains("+") ||
                operacion.contains("-") ||
                operacion.contains("x") ||
                operacion.contains("÷")) {
            return true;
        } else {
            return false;
        }
    }

    public String colocarOperador(String operador) {
        String operacionActual = txtResultado.getText().toString();

        if (dividioPorCero) {
            dividioPorCero = false;
            txtResultado.setTextSize(48);
            return "0" + operador;
        }
        if (operacionActual.startsWith("-")) {
            if (contieneOperador(operacionActual.substring(1))) {
                realizoOperacionPorOperador = true;
                return realizarOperacion(operacionActual) + operador;
            }
        } else {
            if (contieneOperador(operacionActual)) {
                realizoOperacionPorOperador = true;
                return realizarOperacion(operacionActual) + operador;
            }
        }
        if (realizoOperacionPorOperador) {
            realizoOperacion = false;
            realizoOperacionPorOperador = false;
            return operacionActual + operador;
        }
        if (realizoOperacion) {
            realizoOperacion = false;
            return operacionActual + operador;
        }
        if (operacionActual.equals("0") && operador.equals("-")) {
            return "-";
        }
        if (operacionActual.equals("-")) {
            return "0" + operador;
        }

        //Guardamos el caracter de la última posición de la operación.
        String ultimoCaracter = String.valueOf(operacionActual.charAt(operacionActual.length() - 1));

        //Ejemplo: tenemos "24+" y el usuario selecciona el botón de "-", gracias al if se actualiza a "24-"
        String operacionActualizada = "";
        if (esOperador(ultimoCaracter)) {
            //SI TIENE OPERADOR TENEMOS QUE CAMBIARLO.
            String operacionSinOperador = operacionActual.substring(0, operacionActual.length() - 1);

            operacionActualizada = operacionSinOperador + operador;
        } else {
            //SI NO TIENE LO AÑADIMOS DIRECTAMENTE.
            operacionActualizada = operacionActual + operador;
        }
        return operacionActualizada;
    }

    private static boolean esOperador(String ultimoCaracter) {
        return ultimoCaracter.equals("+") || ultimoCaracter.equals("-") || ultimoCaracter.equals("x") || ultimoCaracter.equals("÷");
    }

    public String colocarComa(String operacion) {
        String ultimoNumeroOperacion = "";

        if (realizoOperacion) {
            realizoOperacion = false;
            return operacion + ",";
        }
        if (dividioPorCero) {
            dividioPorCero = false;
            txtResultado.setTextSize(48);
            return "0,";
        }
        if (acabaConOperador(operacion)) {
            return operacion;
        }

        String operacionARevisar = operacion;

        //REVISAR SI TIENE NÚMERO NEGATIVO.
        boolean primerNumeroNegativo = operacion.startsWith("-");
        if (primerNumeroNegativo) {
            operacionARevisar = operacionARevisar.substring(1);
        }

        if (contieneOperador(operacionARevisar)) {
            //TIENE OPERADOR
            String[] partesOperacion = operacionARevisar.split("[+\\-x÷]");
            ultimoNumeroOperacion = partesOperacion[1];
        } else {
            //NO OPERADOR.
            ultimoNumeroOperacion = operacionARevisar;
        }

        if (ultimoNumeroOperacion.contains(",")) {
            return operacion;
        } else {
            return operacion + ",";
        }
    }

    private static boolean acabaConOperador(String operacion) {
        return operacion.endsWith("+") || operacion.endsWith("-") ||
                operacion.endsWith("x") || operacion.endsWith("÷");
    }

    private void borrarUltimoCaracter(String txtOperacion) {
        realizoOperacion = false;
        realizoOperacionPorOperador = false;

        if (dividioPorCero) {
            dividioPorCero = false;
            txtResultado.setTextSize(48);
            txtResultado.setText("0");
            return;
        }
        if (txtOperacion.length() == 1) {
            txtResultado.setText("0");
            return;
        }

        String ultimoCaracterBorrado = txtOperacion.substring(0, txtOperacion.length() - 1);
        ultimoCaracterBorrado = ultimoCaracterBorrado.replace(".", "");
        txtResultado.setText(colocarPunto(ultimoCaracterBorrado));
    }
}
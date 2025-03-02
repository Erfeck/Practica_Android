package com.example.util_idades;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SelectorTemas {

    public static void aplicarTema(Activity activity) {
        SharedPreferences preferencia = activity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String temaActual = preferencia.getString("current_theme", "verde");

        switch (temaActual) {
            case "azul":
                activity.setTheme(R.style.Theme_UtilIdadesAzul);
                break;
            case "naranja":
                activity.setTheme(R.style.Theme_UtilIdadesNaranja);
                break;
            default:
                activity.setTheme(R.style.Theme_UtilIdadesVerde);
                break;
        }
    }

    public static void aplicarTemaCalc(Activity activity) {
        SharedPreferences preferencia = activity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String temaActual = preferencia.getString("current_theme", "verde");

        switch (temaActual) {
            case "azul":
                activity.setTheme(R.style.Theme_CalculadoraAzul);
                break;
            case "naranja":
                activity.setTheme(R.style.Theme_CalculadoraNaranja);
                break;
            default:
                activity.setTheme(R.style.Theme_CalculadoraVerde);
                break;
        }
    }
}

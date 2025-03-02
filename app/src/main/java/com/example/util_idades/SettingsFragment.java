package com.example.util_idades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        SwitchPreferenceCompat modoOscuroSwitch = findPreference("modo_oscuro");

        if (modoOscuroSwitch != null) {
            modoOscuroSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean estaModoOscuro = (boolean) newValue;

                if (estaModoOscuro) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                return true;
            });
        }

        ListPreference preferenciaColor = findPreference("colorApp");
        if (preferenciaColor != null) {
            String valorColor = preferenciaColor.getValue();
            aplicarTema(valorColor);
        }
        preferenciaColor.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String temaSeleccionado = (String) newValue;
                if (!esTemaActual(temaSeleccionado)) {
                    aplicarTema(temaSeleccionado);
                    if (getActivity() != null) {
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
                return true;
            }
        });
    }

    private boolean esTemaActual(String nuevoTema) {
        if (getActivity() == null) return false;

        SharedPreferences preferencias = getActivity()
                .getSharedPreferences("prefs", getActivity().MODE_PRIVATE);
        String temaActual = preferencias.getString("current_theme", "verde");

        return nuevoTema.equals(temaActual);
    }

    private void aplicarTema(String valorColor) {
        if (getActivity() == null) {
            return;
        }
        switch (valorColor) {
            case "verde":
                getActivity().setTheme(R.style.Theme_UtilIdadesVerde);
                break;
            case "azul":
                getActivity().setTheme(R.style.Theme_UtilIdadesAzul);
                break;
            case "naranja":
                getActivity().setTheme(R.style.Theme_UtilIdadesNaranja);
                break;
        }
        // Guardar el tema actual en SharedPreferences
        SharedPreferences.Editor editor = getActivity()
                .getSharedPreferences("prefs", getActivity().MODE_PRIVATE)
                .edit();
        editor.putString("current_theme", valorColor);
        editor.apply();
    }
}
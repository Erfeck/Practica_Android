package com.example.util_idades.api;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiTarea extends AsyncTask<String, Void, Void> {

    private boolean haHabidoError = false;
    private Context context;
    private PersonajeDB personajeSelec;

    public ApiTarea(Context context) {
        this.context = context;
    }

    public ApiTarea(Context context, PersonajeDB personajeSelec) {
        this.context = context;
        this.personajeSelec = personajeSelec;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String cadena = "";
        JSONObject json;
        JSONArray jsonArray;
        try {
            URL url = new URL(ApiActivity.MI_URL);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String linea = null;

            while ((linea = br.readLine()) != null) {
                sb.append(linea).append("\n");
            }
            conexion.disconnect();
            br.close();

            cadena = sb.toString();

            json = new JSONObject(cadena);
            jsonArray = json.getJSONArray("items");

            PersonajeDB unPersonaje;
            String nombre, ki, maxKi, raza, genero, equipo, descripcion, imagen;
            //Imagen
            for (int i = 0; i < jsonArray.length(); i++) {
                nombre = jsonArray.getJSONObject(i).getString("name");
                imagen = jsonArray.getJSONObject(i).getString("image");
                ki = jsonArray.getJSONObject(i).getString("ki");
                maxKi = jsonArray.getJSONObject(i).getString("maxKi");
                raza = jsonArray.getJSONObject(i).getString("race");
                genero = jsonArray.getJSONObject(i).getString("gender");
                equipo = jsonArray.getJSONObject(i).getString("affiliation");
                descripcion = jsonArray.getJSONObject(i).getString("description");


                if (context instanceof ApiActivity) {
                    unPersonaje = new PersonajeDB(nombre, imagen);
                    ApiActivity.listaPersonajes.add(unPersonaje);
                } else {
                    if (personajeSelec.getNombre().equals(nombre)) {
                        unPersonaje = new PersonajeDB(nombre, ki, maxKi, raza, genero, equipo, descripcion, imagen);
                        personajeSelec = unPersonaje;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            haHabidoError = true;
        } catch (JSONException e) {
            e.printStackTrace();
            haHabidoError = true;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if (!haHabidoError) {
            if (context instanceof ApiActivity) {
                ((ApiActivity) context).pintarMonumentos();
            } else if (context instanceof ApiDataActivity) {
                ((ApiDataActivity) context).actualizarInterfaz(personajeSelec);
            }
        } else {
            Toast.makeText(context, "Error al extraer datos", Toast.LENGTH_LONG).show();
        }
    }
}

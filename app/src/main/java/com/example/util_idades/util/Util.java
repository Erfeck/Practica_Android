package com.example.util_idades.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;

public class Util {

    public static byte[] getBytes(Bitmap bitmap) {
        //Conversión de una imagen a un vector de byte
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }
    public static Bitmap getBitmap(byte[] bytes) {
        //conversión de un vector de byte a imagen
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static String capitalizarCadaPalabra(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return cadena;
        }
        String[] cadenaCompleta = cadena.split(" ");
        StringBuilder cadenaActualizada = new StringBuilder();

        for (String unaPalabra : cadenaCompleta) {
            if (!unaPalabra.isEmpty()) {
                // Capitaliza la primera letra de cada palabra y concatena con el resto de la palabra en minúsculas
                cadenaActualizada.append(unaPalabra.substring(0, 1).toUpperCase())
                        .append(unaPalabra.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        // Quita el último espacio y devuelve la cadena capitalizada
        return cadenaActualizada.toString().trim();
    }

    public static void solicitarPermisos(final String permiso, String justificacion, final int requestCode, final Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permiso)) {
            new AlertDialog.Builder(activity).
                    setTitle("Solicitud de permiso Galería").
                    setMessage(justificacion).
                    setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, new String[] {permiso}, requestCode);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[] {permiso}, requestCode);
        }
    }
}
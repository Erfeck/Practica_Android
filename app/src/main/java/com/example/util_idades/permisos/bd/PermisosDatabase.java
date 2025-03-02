package com.example.util_idades.permisos.bd;

import static android.provider.BaseColumns._ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;


import com.example.util_idades.util.Util;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class PermisosDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public PermisosDatabase(@Nullable Context context) {
        super(context, PermisosConstantes.TABLA_FOTO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PermisosConstantes.TABLA_FOTO + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PermisosConstantes.FOTO + " BLOB, " +
                PermisosConstantes.FECHA_FOTO + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PermisosConstantes.TABLA_FOTO);
        onCreate(db);
    }

    public void agregarFoto(Bitmap fotoBitmap, String fechaFoto) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        fotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imagenBytes = baos.toByteArray();

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PermisosConstantes.FOTO, imagenBytes);
        values.put(PermisosConstantes.FECHA_FOTO, fechaFoto);

        db.insertOrThrow(PermisosConstantes.TABLA_FOTO, null, values);
        db.close();
    }

    public Bitmap getFotoDelDia(String fechaDelDia) {
        final String[] SELECT = {_ID, PermisosConstantes.FOTO, PermisosConstantes.FECHA_FOTO};

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(PermisosConstantes.TABLA_FOTO,
                SELECT, null, null, null, null, _ID);

        while (cursor.moveToNext()) {
            byte[] foto = cursor.getBlob(1);
            String fechaFoto = cursor.getString(2);

            if (fechaDelDia.equals(fechaFoto)) {
                return Util.getBitmap(foto);
            }
        }

        cursor.close();
        db.close();
        return null;
    }

    public HashMap<byte[],String> getFotos() {
        HashMap<byte[], String> listaFotos = new HashMap<>();

        final String[] SELECT = {_ID, PermisosConstantes.FOTO, PermisosConstantes.FECHA_FOTO};

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(PermisosConstantes.TABLA_FOTO,
                SELECT, null, null, null, null, _ID + " DESC");

        while (cursor.moveToNext()) {
            byte[] foto = cursor.getBlob(1);
            String fechaFoto = cursor.getString(2);

            listaFotos.put(foto,fechaFoto);
        }

        cursor.close();
        db.close();
        return listaFotos;
    }

    public boolean existeFotoDeEseDia(String fechaAValidar) {
        final String[] SELECT = {PermisosConstantes.FECHA_FOTO};

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(PermisosConstantes.TABLA_FOTO,
                SELECT, null, null, null, null, _ID);

        while (cursor.moveToNext()) {
            String fechaExistente = cursor.getString(0);

            if (fechaExistente.equals(fechaAValidar)) {
                cursor.close();
                db.close();
                return true;
            }
        }

        cursor.close();
        db.close();
        return false;
    }

    public void actualizarFoto(Bitmap fotoBitmap, String fechaFoto) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        fotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imagenBytes = baos.toByteArray();

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PermisosConstantes.FOTO, imagenBytes); // Guardar la nueva imagen

        // Hacer la actualización solo si la fecha coincide
        int rowsAffected = db.update(PermisosConstantes.TABLA_FOTO, values,
                PermisosConstantes.FECHA_FOTO + " = ?", new String[]{fechaFoto});

        // Si rowsAffected es mayor que 0, significa que se actualizó una fila
        if (rowsAffected > 0) {
            // La actualización fue exitosa
            System.out.println("Foto actualizada exitosamente.");
        } else {
            // Si no se actualizó ninguna fila, la fecha no existe
            System.out.println("No se encontró una foto para la fecha proporcionada.");
        }

        // Cerrar la base de datos
        db.close();
    }
}
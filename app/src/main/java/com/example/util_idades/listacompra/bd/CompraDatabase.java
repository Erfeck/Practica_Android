package com.example.util_idades.listacompra.bd;

import static android.provider.BaseColumns._ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.util_idades.listacompra.productos.Categoria;
import com.example.util_idades.listacompra.productos.ElementoCompra;
import com.example.util_idades.util.Util;

import java.util.ArrayList;
import java.util.List;

public class CompraDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public CompraDatabase(@Nullable Context context) {
        super(context, CompraConstantes.TABLA_COMPRA, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CompraConstantes.TABLA_COMPRA + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CompraConstantes.NOMBRE_COMPRA + " TEXT, " +
                CompraConstantes.ID_CATEGORIA + " INTEGER, " +
                "FOREIGN KEY (" + CompraConstantes.ID_CATEGORIA + ") REFERENCES " + CompraConstantes.TABLA_CATEGORIA + "(" + _ID + "))");

        db.execSQL("CREATE TABLE " + CompraConstantes.TABLA_CATEGORIA + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CompraConstantes.NOMBRE_CATEGORIA + " TEXT, " +
                CompraConstantes.FOTO + " BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CompraConstantes.TABLA_COMPRA);
        db.execSQL("DROP TABLE IF EXISTS " + CompraConstantes.TABLA_CATEGORIA);
        onCreate(db);
    }

    public void nuevoElementoCompra(ElementoCompra unElemento, int idCategoria) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CompraConstantes.NOMBRE_COMPRA, unElemento.getTexto().toLowerCase());
        values.put(CompraConstantes.ID_CATEGORIA, idCategoria);

        db.insertOrThrow(CompraConstantes.TABLA_COMPRA, null, values);
        db.close();
    }

    public void nuevaCategoria(Categoria unaCategoria) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CompraConstantes.NOMBRE_CATEGORIA, unaCategoria.getNombre().toLowerCase());
        values.put(CompraConstantes.FOTO, Util.getBytes(unaCategoria.getFoto()));

        db.insertOrThrow(CompraConstantes.TABLA_CATEGORIA, null, values);
        db.close();
    }

    public void eliminarElemento(String nombreElemento) {
        nombreElemento = nombreElemento.toLowerCase();

        SQLiteDatabase db = getWritableDatabase();

        String[] argumentos = {nombreElemento};
        db.delete(CompraConstantes.TABLA_COMPRA, CompraConstantes.NOMBRE_COMPRA + "=?", argumentos);

        db.close();
    }

    public List<ElementoCompra> getElementosCompra() {
        List<ElementoCompra> listaCompra = new ArrayList<>();
        ElementoCompra nuevoElemento = null;
        final String[] SELECT = {_ID, CompraConstantes.NOMBRE_COMPRA, CompraConstantes.ID_CATEGORIA};

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(CompraConstantes.TABLA_COMPRA, SELECT, null, null, null, null, _ID);

        while (cursor.moveToNext()) {
            int idElemento = cursor.getInt(0);
            String nombreElemento = cursor.getString(1);
            int idCategoria = cursor.getInt(2);
            nuevoElemento = new ElementoCompra( nombreElemento,false, idCategoria);
            listaCompra.add(nuevoElemento);
        }

        cursor.close();
        db.close();
        return listaCompra;
    }
    public List<Categoria> getCategorias() {
        List<Categoria> listaCategorias = new ArrayList<>();
        Categoria unaCategoria = null;
        final String[] SELECT = {_ID, CompraConstantes.NOMBRE_CATEGORIA, CompraConstantes.FOTO};

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(CompraConstantes.TABLA_CATEGORIA, SELECT, null, null, null, null, _ID);

        while (cursor.moveToNext()) {
            int idCategoria = cursor.getInt(0);
            String nombreCategoria = cursor.getString(1);
            byte[] imagen = cursor.getBlob(2);
            unaCategoria = new Categoria(idCategoria,nombreCategoria,Util.getBitmap(imagen));
            listaCategorias.add(unaCategoria);
        }

        cursor.close();
        db.close();
        return listaCategorias;
    }

    public boolean existeNombre(String nombreAValidar) {
        nombreAValidar = nombreAValidar.toLowerCase();

        final String[] SELECT = {_ID, CompraConstantes.NOMBRE_COMPRA};

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(CompraConstantes.TABLA_COMPRA, SELECT, null, null, null, null, _ID);

        while (cursor.moveToNext()) {
            String nombreElemento = cursor.getString(1);
            if (nombreElemento.equals(nombreAValidar)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeNombreCategoria(String nombreCategoriaAValidar) {
        nombreCategoriaAValidar = nombreCategoriaAValidar.toLowerCase();

        final String[] SELECT = {_ID, CompraConstantes.NOMBRE_CATEGORIA};

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(CompraConstantes.TABLA_CATEGORIA, SELECT, null, null, null, null, _ID);

        while (cursor.moveToNext()) {
            String nombreRegistrado = cursor.getString(1);
            if (nombreCategoriaAValidar.equals(nombreRegistrado)) {
                return true;
            }
        }
        return false;
    }
}
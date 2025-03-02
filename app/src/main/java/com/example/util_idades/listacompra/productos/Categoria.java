package com.example.util_idades.listacompra.productos;

import android.graphics.Bitmap;

public class Categoria {

    private int id;
    private String nombre;
    private Bitmap foto;

    public Categoria(int id, String nombre, Bitmap foto) {
        this.foto = foto;
        this.id = id;
        this.nombre = nombre;
    }

    public Categoria(String nombre, Bitmap foto) {
        this.foto = foto;
        this.nombre = nombre;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "foto=" + foto +
                ", id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

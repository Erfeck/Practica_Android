package com.example.util_idades.api;

import java.io.Serializable;

public class PersonajeDB implements Serializable {

    private String nombre;
    private String ki;
    private String maxKi;
    private String raza;
    private String genero;
    private String equipo;
    private String descripcion;
    private String image;

    public PersonajeDB(String nombre, String ki, String maxKi, String raza, String genero, String equipo, String descripcion, String image) {
        this.nombre = nombre;
        this.ki = ki;
        this.maxKi = maxKi;
        this.raza = raza;
        this.genero = genero;
        this.equipo = equipo;
        this.descripcion = descripcion;
        this.image = image;
    }

    public PersonajeDB(String nombre, String image) {
        this.nombre = nombre;
        this.image = image;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getEquipo() {
        return equipo;
    }
    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getKi() {
        return ki;
    }
    public void setKi(String ki) {
        this.ki = ki;
    }
    public String getMaxKi() {
        return maxKi;
    }
    public void setMaxKi(String maxKi) {
        this.maxKi = maxKi;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getRaza() {
        return raza;
    }
    public void setRaza(String raza) {
        this.raza = raza;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
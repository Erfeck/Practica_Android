package com.example.util_idades.listacompra.productos;

public class ElementoCompra {

    private String texto;
    private boolean estaCompletado;
    private int idCategoria;

    public ElementoCompra(String texto, boolean estaCompletado, int idCategoria)  {
        this.texto = texto;
        this.estaCompletado = estaCompletado;
        this.idCategoria = idCategoria;

    }

    public boolean isEstaCompletado() {
        return estaCompletado;
    }

    public void setEstaCompletado(boolean estaCompletado) {
        this.estaCompletado = estaCompletado;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
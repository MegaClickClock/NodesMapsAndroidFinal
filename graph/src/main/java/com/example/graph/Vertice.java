package com.example.graph;

import java.io.Serializable;

public class Vertice implements Serializable {

    private Nodo origen;
    private Nodo destino;
    private Integer precio;
    private Integer trafico;

    public Vertice(Nodo origen, Nodo destino, Integer precio, Integer trafico) {
        this.destino = destino;
        this.origen = origen;
        this.precio = precio;
        this.trafico = trafico;
    }

    public Nodo getOrigen() {
        return origen;
    }

    public void setOrigen(Nodo origen) {
        this.origen = origen;
    }

    public Nodo getDestino() {
        return destino;
    }

    public void setDestino(Nodo destino) {
        this.destino = destino;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getTrafico() {
        return trafico;
    }

    public void setTrafico(Integer trafico) {
        this.trafico = trafico;
    }

    public Integer getCost() {
        return this.trafico;
    }
}

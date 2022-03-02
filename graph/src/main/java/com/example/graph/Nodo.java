package com.example.graph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Nodo implements Serializable {

    private String id;
    private String name;
    private Double latitud;
    private Double longitud;
    private Set<Vertice> vertices;
    private Map<String, RutaNodo> rutaNodoMap;

    public Nodo(String id, String name, Double latitud, Double longitud) {
        this.id = id;
        this.name = name;
        this.latitud = latitud;
        this.longitud = longitud;
        this.vertices = new HashSet<>();
        this.rutaNodoMap = new HashMap<>();
    }

    public void addVertice(Vertice vertice) {
        this.vertices.add(vertice);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Set<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(Set<Vertice> vertices) {
        this.vertices = vertices;
    }


    @Override
    public String toString() {
        return this.id+ ": " + this.name;
    }

    public String getId() {
        return id;
    }

    public Set<Vertice> getVerticesDestino() {
        Set<Vertice> destino = new HashSet<>();
        for (Vertice vertice: this.vertices) {
            if (vertice.getOrigen().equals(this)) {
                destino.add(vertice);
            }
        }
        return destino;
    }

    public void setMaxDistances(Map<String, Nodo> nodos) {
        for (Nodo n: nodos.values()) {
            this.rutaNodoMap.put(n.getId(), new RutaNodo());
        }
    }

    public void updateDistance(Nodo origen, int nuevoCosto, Nodo previo) {
        RutaNodo ruta = this.rutaNodoMap.get(origen.getId());
        if (nuevoCosto < ruta.costo) {
            ruta.costo = nuevoCosto;
            ruta.previo = previo;
        }
    }

    public Integer getDistance(Nodo iter) {
        return this.rutaNodoMap.get(iter.getId()).costo;
    }

    public List<Nodo> getCamino(Nodo destino) {
        if (this.rutaNodoMap.isEmpty() ||
                !this.rutaNodoMap.containsKey(destino.getId())) {
            return null;
        }

        LinkedList<Nodo> camino = new LinkedList<>();
        Nodo iter = destino;
        camino.addFirst(destino);
        while (iter != this) {
            iter = rutaNodoMap.get(iter.getId()).previo;
            camino.addFirst(iter);

        }
        return camino;
    }

    private class RutaNodo implements Serializable {
        public Nodo previo;
        public Integer costo;
        public RutaNodo() {
            this.previo = null;
            this.costo = Integer.MAX_VALUE;
        }
    }
}

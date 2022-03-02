package com.example.graph;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Graph implements Serializable {

    private Map<String, Nodo> nodos;

    public Graph() {
        this.nodos = new HashMap<>();
    }

    public Map<String, Nodo> getNodos() {
        return nodos;
    }

    public void addNodo(Nodo nodo) throws GraphException {
        if (this.nodos.containsKey(nodo.getId())) {
            throw new GraphException("El nodo ya existe");
        }

        this.nodos.put(nodo.getId(), nodo);
    }

    public void addVertice(Vertice vertice) {
        Nodo origen = vertice.getOrigen();
        Nodo destino = vertice.getDestino();

        origen.addVertice(vertice);
        destino.addVertice(vertice);
    }

    public String toJson() {

        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Vertice.class, new VerticeSerializer())
                    .registerTypeAdapter(Nodo.class, new NodoSerializer())
                    .setPrettyPrinting()
                    .create();
            return gson.toJson(this);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static Graph fromJson(String graphStr) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Graph.class, new GraphDeserializer())
                    .setPrettyPrinting()
                    .create();
            return gson.fromJson(graphStr, Graph.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Nodo> calcularCaminoCorto(Nodo origen, Nodo destino) throws GraphException {
        // Verificar que los nodos existan
        if (!this.nodos.containsKey(origen.getId()) || !this.nodos.containsKey(destino.getId())) {
            throw new GraphException("Destino|Origen no existe en el grafo");
        }

        Queue<Nodo> queue = new LinkedList<>();
        Set<Nodo> visitado = new HashSet<>();

        queue.add(origen);
        visitado.add(origen);

        origen.setMaxDistances(this.getNodos());
        origen.updateDistance(origen, 0, origen);

        while (!queue.isEmpty()) {
            Nodo iter = queue.remove();

            System.out.println("Nodo: " + iter.getName());
            for (Vertice vecino: iter.getVertices()) {
                Nodo d = vecino.getDestino();
                if (!visitado.contains(d)) {
                    origen.updateDistance(d,
                            vecino.getCost() + origen.getDistance(iter),
                            iter);
                    visitado.add(d);
                    queue.add(d);
                }
            }
        }

        return origen.getCamino(destino);
    }

    public static void main(String[] args) throws GraphException, IOException {
        Graph graph = new Graph();
        Nodo n1 = new Nodo("1", "Plaza principal", -17.7841265, -63.1816848);
        Nodo n2 = new Nodo("2", "Fexpocruz", -17.7736256,-63.1940721);
        Nodo n3 = new Nodo("3", "Parque Urbano", -17.7855725,-63.1905101);
        Nodo n4 = new Nodo("4", "La Ramada", -17.7855725,-63.1905101);
        graph.addNodo(n1);
        graph.addNodo(n2);
        graph.addNodo(n3);
        graph.addNodo(n4);
        graph.addVertice(new Vertice(n1, n2, 4, 5));
        graph.addVertice(new Vertice(n2, n3, 4, 3));
        graph.addVertice(new Vertice(n2, n4,24, 6));
        graph.addVertice(new Vertice(n3, n4, 2, 6));

        String filename = "GrafoNodoV.json";
        String str = Files.readString(Path.of(filename));
        Graph g2 = Graph.fromJson(str);
        List<Nodo> camino = g2.calcularCaminoCorto(g2.getNodos().get("A02"), g2.getNodos().get("A08"));
        for (Nodo c : camino) {
            System.out.println("id: " + c.getId() + " nombre: " + c.getName());
        }
        //System.out.println(graph.toJson());
    }
}

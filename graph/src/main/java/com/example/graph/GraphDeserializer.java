package com.example.graph;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class GraphDeserializer implements JsonDeserializer<Graph> {

    @Override
    public Graph deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        GraphJson graphJson =  new Gson().fromJson(json, GraphJson.class);
        Graph g = new Graph();

        try {
            for (String nodoId :graphJson.nodos.keySet()) {
                NodoJson nodoJson = graphJson.nodos.get(nodoId);
                Nodo n = new Nodo(nodoId, nodoJson.nombre, nodoJson.latitud, nodoJson.longitud);
                g.addNodo(n);
            }
            for (String nodoId :graphJson.nodos.keySet()) {
                NodoJson nodoJson = graphJson.nodos.get(nodoId);

                for (VerticeJson verticeJson:nodoJson.vertices) {
                    Nodo origen = g.getNodos().get(nodoId);
                    Nodo destino = g.getNodos().get(verticeJson.destino);
                    if (origen == null) {
                        System.out.println("Nodo origen no existe: " + nodoId);
                        continue;
                    }
                    if (destino == null) {
                        System.out.println("Nodo destino no existe: " + verticeJson.destino);
                        continue;
                    }

                    Vertice v = new Vertice(
                            origen,
                            destino,
                            verticeJson.precio,
                            verticeJson.trafico
                    );
                    g.addVertice(v);
                }
            }
        } catch (GraphException e) {
            e.printStackTrace();
        }
        return g;
    }
    public class GraphJson {
        Map<String, NodoJson> nodos;
    }
    public class NodoJson {
        String nombre;
        Double latitud;
        Double longitud;
        Set<VerticeJson> vertices;
    }
    public class VerticeJson {
        private String destino;
        private Integer precio;
        private Integer trafico;
    }
}

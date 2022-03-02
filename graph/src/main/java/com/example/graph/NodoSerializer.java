package com.example.graph;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class NodoSerializer implements JsonSerializer<Nodo> {
    @Override
    public JsonElement serialize(Nodo src, Type typeOfSrc, JsonSerializationContext context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Vertice.class, new VerticeSerializer())
                .create();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nombre", src.getName());
        jsonObject.addProperty("latitud", src.getLatitud());
        jsonObject.addProperty("longitud", src.getLongitud());
        JsonElement vertices = gson.toJsonTree(src.getVerticesDestino());
        jsonObject.add("vertices", vertices);
        return jsonObject;
    }
}

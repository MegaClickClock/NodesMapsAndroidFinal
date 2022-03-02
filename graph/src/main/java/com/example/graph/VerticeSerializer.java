package com.example.graph;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class VerticeSerializer implements JsonSerializer<Vertice> {
    @Override
    public JsonElement serialize(Vertice vertice, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("destino", vertice.getDestino().getId());
        jsonObject.addProperty("precio", vertice.getPrecio());
        jsonObject.addProperty("trafico", vertice.getTrafico());
        return jsonObject;
    }
}

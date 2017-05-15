package com.appartika.telegram;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class KeyboardButton {
    public String text = "";

    public KeyboardButton(String text) {
        this.text = text;

    }
    public JsonArray toJSON() {
        JsonArray ja = new JsonArray();
        JsonObject jo = new JsonObject();
        jo.addProperty("text", this.text);
        ja.add(jo);
        return ja;
    }
}
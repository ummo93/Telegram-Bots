package com.github.ummo93.telegram;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class InlineButton {
    public String text = "";
    public boolean isURL = false;
    public String url = "";
    public String callback = "";

    public InlineButton(String text, boolean isURL, String url) {
        this.text = text;
        this.isURL = isURL;
        this.url = url;
    }
    public InlineButton(String text, String callback) {
        this.text = text;
        this.callback = callback;
    }
    public JsonArray toJSON() {
        return this.isURL ? parseURL() : parseCallbackButton();
    }

    private JsonArray parseCallbackButton() {
        JsonArray ja = new JsonArray();
        JsonObject jo = new JsonObject();
        jo.addProperty("text", this.text);
        jo.addProperty("callback_data",  this.callback);
        ja.add(jo);
        return ja;
    }
    private JsonArray parseURL() {
        JsonArray ja = new JsonArray();
        JsonObject jo = new JsonObject();
        jo.addProperty("text", this.text);
        jo.addProperty("url",  this.url);
        ja.add(jo);
        return ja;
    }
}
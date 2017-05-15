package com.appartika.telegram;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ReplyKeyboard {
    private JsonArray arr;

    public ReplyKeyboard(KeyboardButton... buttons) {
        arr = new JsonArray();
        for (KeyboardButton button : buttons) {
            this.arr.add(button.toJSON());
        }
    }
    public ReplyKeyboard() {
        arr = new JsonArray();
    }
    public void addButton(KeyboardButton rb) {
        this.arr.add(rb.toJSON());
    }

    public String toJSON() {
        JsonObject jo = new JsonObject();
        jo.add("keyboard", this.arr);
        return jo.toString();
    }
}

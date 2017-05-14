package com.github.ummo93.telegram;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class InlineKeyboard {
    private JsonArray arr;

    public InlineKeyboard(InlineButton... buttons) {
        arr = new JsonArray();
        for (InlineButton button : buttons) {
            this.arr.add(button.toJSON());
        }
    }
    public InlineKeyboard() {
        arr = new JsonArray();
    }
    public void addButton(InlineButton ib) {
        this.arr.add(ib.toJSON());
    }

    public String toJSON() {
        JsonObject jo = new JsonObject();
        jo.add("inline_keyboard", this.arr);
        return jo.toString();
    }
}

package com.github.ummo93;

public class TextMessage extends Message {
    public String text;
    public TextMessage(String id, int date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }
}
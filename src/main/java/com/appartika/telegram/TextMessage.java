package com.appartika.telegram;

public class TextMessage extends Message {
    public String text;
    public TextMessage(String id, int date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }
}
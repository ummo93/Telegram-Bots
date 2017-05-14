package com.appartika.telegram;

public class Chat {

    public String id;
    public String first_name;
    public String last_name;

    public Chat(String id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public void post(Audio audio) { TelegramBot.send(this.id, audio); }
    public void post(String text) { TelegramBot.send(this.id, text); }
    public void post(Photo photo) { TelegramBot.send(this.id, photo); }
    public void post(String text, InlineKeyboard ik) { TelegramBot.send(this.id, text, ik); }
}
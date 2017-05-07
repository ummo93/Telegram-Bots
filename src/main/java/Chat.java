public class Chat {

    String id;
    String first_name;
    String last_name;

    Chat(String id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }
    void sendPhoto(String url) {
        TelegramBot.sendPhoto(this.id, url);
    }
    void post(String text) {
        TelegramBot.send(this.id, text);
    }
    void post(String text, InlineKeyboard ik) {
        TelegramBot.send(this.id, text, ik);
    }
}
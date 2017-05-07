class TextMessage extends Message {
    String text;
    TextMessage(String id, int date, String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }
}
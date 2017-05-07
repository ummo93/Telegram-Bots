public class Main {
    
    static final String token = "<Your_bot_token>";

    public static void main(String[] args) {

        TelegramBot bot = new TelegramBot(token);

        bot.getAction((String payload, Chat dialog) -> {
            if (payload.equals("image"))
            dialog.sendPhoto("http://online-teacher.ru/image/french/eda.jpg");
            else
            dialog.post("Your variant is: " + payload);
            return true;
        });

        bot.getMessage((TextMessage message, Chat dialog) -> {
            switch(message.text.toLowerCase()) {
                case("hello"):
                    dialog.post("Hello!");
                break;
                case("hi"):
                    dialog.post("And you, how are you?");
                break;
                case("/menu"):
                    InlineButton[] buttons = {
                        new InlineButton("Button1", "but_1"),
                        new InlineButton("Button2", "but_2"),
                        new InlineButton("Image", "image")
                    };
                    InlineKeyboard inlines = new InlineKeyboard(buttons);
                    dialog.post("Ok, this is menu:", inlines);
                break;
                default:
                    dialog.post(TelegramBot.askForHelp(message.text));
            }
            return true;
        });

        bot.setPolling(1000L).run();

    }
}
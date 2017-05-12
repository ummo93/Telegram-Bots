public class Main {
    
    private static final String token = "392121257:AAGrLhQELYQ1Zj_-O8asyH2nabz63TtGmsc";

    public static void main(String[] args) {

        TelegramBot bot = new TelegramBot(token);

        bot.getAction((String payload, Chat dialog) -> {
            if (payload.equals("image"))
            dialog.sendPhoto("http://online-teacher.ru/image/french/eda.jpg");
            else
            dialog.post("Your variant is: " + payload);
        });

        bot.getMessage((TextMessage message, Chat dialog) -> {

            new Reflect("^([H|h]ello|[H|h]i).*") {
                @Override
                void response(String match) {
                    dialog.post("And you hello :)");
                }
            };

            new Reflect(".*([A|a]uthor|[C|c]reator).*") {
                @Override
                void response(String match) {
                    dialog.post("My father is ummo93 https://github.com/ummo93");
                }
            };

            new Reflect("/menu") {
                @Override
                void response(String match) {
                    InlineKeyboard inlines = new InlineKeyboard(
                            new InlineButton("Button1", "but_1"),
                            new InlineButton("Button2", "but_2"),
                            new InlineButton("Image", "image")
                    );
                    dialog.post("Ok, this is menu:", inlines);
                }
            };

            new Reflect() {
                @Override
                void response() {
                    dialog.post(TelegramBot.askForHelp(message.text));
                }
            };

        });

        bot.polling().run();
    }
}
import com.github.ummo93.*;

public class Main {

    private static final String token = "392121257:AAGrLhQELYQ1Zj_-O8asyH2nabz63TtGmsc";

    public static void main(String[] args) {

        TelegramBot bot = new TelegramBot(token);

        bot.getAction((String payload, Chat dialog) -> {
            switch (payload) {
                case "image":
                    Photo photo = new Photo("http://online-teacher.ru/image/french/eda.jpg",
                            "This is a photo caption. It has a limit with 0-200 chars size");
                    dialog.post(photo);
                    break;
                case "audio":
                    Audio audio = new Audio("http://www.noiseaddicts.com/samples_1w72b820/2553.mp3",
                            "This is a caption");
                    dialog.post(audio);
                    break;
                default:
                    dialog.post("Your variant is: " + payload);
                    break;
            }
        });

        bot.getMessage((TextMessage message, Chat dialog) -> {

            new Reflect("^([H|h]ello|[H|h]i).*") {
                @Override
                public void response(String match) {
                    dialog.post("And you hello :)");
                }
            };

            new Reflect(".*([A|a]uthor|[C|c]reator).*") {
                @Override
                public void response(String match) {
                    dialog.post("My father is ummo93 https://github.com/ummo93");
                }
            };

            new Reflect("/start") {
                @Override
                public void response(String match) {
                    dialog.post("Hello, send /menu for managing me");
                }
            };

            new Reflect("/menu") {
                @Override
                public void response(String match) {
                    InlineKeyboard inlines = new InlineKeyboard(
                            new InlineButton("Button1", "but_1"),
                            new InlineButton("Button2", "but_2"),
                            new InlineButton("Image", "image"),
                            new InlineButton("Audio", "audio")
                    );
                    dialog.post("Ok, this is menu:", inlines);
                }
            };

            new Reflect() {
                @Override
                public void response() {
                    dialog.post(TelegramBot.askForHelp(message.text));
                }
            };

        });

        bot.polling().run();
    }
}
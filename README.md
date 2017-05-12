# Easy telegram bot library

![img](https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/Telegram_text_logo.svg/500px-Telegram_text_logo.svg.png)

#### Description
Just create a bot with telegram "botfather", config handlers on text and action (callbacks) messages with this library.
Library not packed yet, for simplify it usage, but it is in the plans.

#### For example:
 ```java
String token = "123123123:ASAddmafawfqWD-123FwqafwaWFAf"; // Your bot token from botfather

TelegramBot bot = new TelegramBot(token);                  // Creates a bot object

bot.getAction((String payload, Chat dialog) -> {           // Print all action messages
    System.out.println(payload);
});

bot.getMessage((TextMessage message, Chat dialog) -> {     // Reflection
    
    new Reflect("^([H|h]ello|[H|h]i).*") {
        @Override
        void response(String match) {
            dialog.post("And you hello :)");
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
    
    // It's mean that if bot dont know, he send messege to his friend
    new Reflect() {
        @Override
        void response() {
            dialog.post(TelegramBot.askForHelp(message.text));
        }
    };
});

bot.polling().run();                                       // Check updates with long polling
 ```

#### Supported message types
Now this lib. supported only:
- Text message,
- Message with images (using external url),
- Text message with Inline Keyboard Markup
- Callback message (actions from keyboard)
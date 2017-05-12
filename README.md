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

bot.getMessage((TextMessage message, Chat dialog) -> {     // Return to the user his message
    dialog.post(message.text);
});

bot.polling().run();                                       // Check updates with long polling
 ```

#### Supported message types
Now this lib. supported only:
- Text message,
- Message with images (using external url),
- Text message with Inline Keyboard Markup
- Callback message (actions from keyboard)
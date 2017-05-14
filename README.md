# Easy telegram bot library
[![Repo](https://jitpack.io/v/ummo93/Telegram-Bots.svg)](https://jitpack.io/#ummo93/Telegram-Bots)
![img](https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/Telegram_text_logo.svg/500px-Telegram_text_logo.svg.png)

#### Description
Just create a bot with telegram "botfather", config handlers on text and action (callbacks) messages with this library.

#### Getting started:
P/S: You may find a sample application in src/main/test folder, replace a bot token and try it yourself!

##### Dependencies:
  - GSON for json parsing (https://github.com/google/gson)
  - org.apache.httpcomponents Http client
  
##### Installation as maven dependency

In pom.xml file there should be an instruction containing the repository address:
 ```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
 ```
 
 And a dependency name in dependencies section:
 ```xml
	<dependency>
	    <groupId>com.github.ummo93</groupId>
	    <artifactId>Telegram-Bots</artifactId>
	    <version>0.9.1</version>
	</dependency>
 ```

#### For example:
 ```java
import com.appartika.telegram.*;
 
String token = "392121257:AAGrLhQELYQ1Zj_-O8asyH2nabz63TtGmsc"; // Your bot token from botfather

TelegramBot bot = new TelegramBot(token);                       // Creates a bot object

bot.getAction((String payload, Chat dialog) -> {                // Print all action messages
    System.out.println(payload);
});

bot.getMessage((TextMessage message, Chat dialog) -> {          // Reflection
    
    new Reflect("^([H|h]ello|[H|h]i).*") {
        @Override
        public void response(String match) {
            dialog.post("And you hello :)");
        }
    };
    
    new Reflect("/menu") {
        @Override
        public void response(String match) {
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
        public void response() {
            dialog.post(TelegramBot.askForHelp(message.text));
        }
    };
});

bot.polling().run(); // Check updates with long polling
 ```

#### Supported message types
Now this lib. supported a basic content types, such as:
- Text message,
- Message with images and audio (using external url),
- Text message with Inline Keyboard Markup
- Callback message (actions from keyboard)
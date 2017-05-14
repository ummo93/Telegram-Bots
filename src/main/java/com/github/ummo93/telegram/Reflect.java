package com.github.ummo93.telegram;

import java.util.regex.*;

public class Reflect {

    private static boolean isUnhandledError = true;

    public Reflect(String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(TelegramBot.reflection);
        if (matcher.matches()) {
            response(matcher.toMatchResult().group());
            Reflect.isUnhandledError = false;
        }

    }
    public Reflect() {
        if(isUnhandledError) {
            response();
        } else {
            Reflect.isUnhandledError = true;
        }
    }

    public void response(String match){}
    public void response(){}
}

import java.util.regex.*;

public class Reflect {

    private static boolean isUnhandledError = true;

    Reflect(String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(TelegramBot.reflection);
        if (matcher.matches()) {
            response(matcher.toMatchResult().group());
            Reflect.isUnhandledError = false;
        }

    }
    Reflect() {
        if(isUnhandledError) {
            response();
        } else {
            Reflect.isUnhandledError = true;
        }
    }

    void response(String match){}
    void response(){}
}

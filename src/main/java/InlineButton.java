import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class InlineButton {
    public String text = "";
    public boolean isURL = false;
    public String url = "";
    public String callback = "";

    InlineButton(String text, boolean isURL, String url) {
        this.text = text;
        this.isURL = isURL;
        this.url = url;
    }
    InlineButton(String text, String callback) {
        this.text = text;
        this.callback = callback;
    }
    public JsonArray toJSON() {
        JsonArray result = this.isURL ? parseURL() : parseCallbackButton();
        return result;
    }

    private JsonArray parseCallbackButton() {
        JsonArray ja = new JsonArray();
        JsonObject jo = new JsonObject();
        jo.addProperty("text", this.text);
        jo.addProperty("callback_data",  this.callback);
        ja.add(jo);
        return ja;
    }
    private JsonArray parseURL() {
        JsonArray ja = new JsonArray();
        JsonObject jo = new JsonObject();
        jo.addProperty("text", this.text);
        jo.addProperty("url",  this.url);
        ja.add(jo);
        return ja;
    }
}
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.google.gson.*;


public class TelegramBot {

    private static long pollingInterval = 500L;
    private static int lastMessageTimestamp = 0;
    private static String addressWebhook = "https://api.telegram.org/bot";
    private static String updateMethod = "/getUpdates?offset=-1";
    private static String sendingMethod = "/sendMessage";
    private static String sendPhotoMethod = "/sendPhoto";
    private TextEvent<TextMessage, Chat> reaction;
    private ActionEvent<String, Chat> reactionCallback;

    /**
     * Create a bot object. Example of usage:
     * <pre>
     * TelegramBot bot = new TelegramBot("1234567:ABCDEFGH$-01231141ASAFGQ1");
     * bot.getMessage((TextMessage message, Chat dialog) -> {
     *     dialog.post("Hello!");
     * }
     * bot.setPolling(1500L).run();
     * </pre>
     */
    public TelegramBot(String address) {
        try {
            System.out.println("Bot has been created and hears a chat...");
            addressWebhook += address;
            // Получаем первый timestamp
            String firstRequestBody = TelegramBot.getUpdates();
            JsonArray result = getJson(firstRequestBody).getAsJsonArray("result");
            lastMessageTimestamp = result.get(0).getAsJsonObject().get("message").getAsJsonObject().get("date").getAsInt();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Registration a lambda which activates when bot get an text message
     * <pre>
     * bot.getMessage((TextMessage message, Chat dialog) -> {
     *     // What is going to happen 
     * }
     * </pre>
     */
    public void getMessage(TextEvent<TextMessage, Chat> lambda) {
        this.reaction = lambda;
    }

    /**
     * Registration a lambda which activates when bot get an action
     * (for example, user click to inline button)
     * <pre>
     * bot.getAction((String payload, Chat dialog) -> {
     *     // What is going to happen 
     * }
     * </pre>
     */
    public void getAction(ActionEvent<String, Chat> lambda) {
        this.reactionCallback = lambda;
    }

    /**
     * Get updates for bot actions
     * @return body String json string with updates
     */
    private static String getUpdates() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(addressWebhook + updateMethod);
        CloseableHttpResponse response = httpclient.execute(httpget);
        int data = response.getEntity().getContent().read();
        char content;
        String body = "";
        while(data != -1) {
            content = (char) data;
            body += content;
            data = response.getEntity().getContent().read();
        }
        httpclient.close();
        return body;
    }

    /**
     * Configure an interal for short polling process
     * @param interval long interval for short polling
     */
    public TelegramBot setPolling(long interval) {
        pollingInterval = interval;
        updateMethod += "&timeout=" + ((int) pollingInterval/1000);
        return this;
    }

    /**
     * Start bot's lifecycle and polling process
     */
    void run() {
        try {
            String updates = TelegramBot.getUpdates();
            JsonArray result = getJson(updates).getAsJsonArray("result");
            JsonElement event = result.get(0);
            if(event.getAsJsonObject().has("callback_query")) {
                // Callback
                this.callbackParse(event);
            } else {
                // TextMessage
                this.textParse(event);
            }
            Thread.sleep(pollingInterval);
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } finally {
            run();
        }
    }

    private void callbackParse(JsonElement event) {
        int timestamp = event.getAsJsonObject().get("update_id").getAsInt();
        if(timestamp != lastMessageTimestamp) {
            System.out.println(event);
            JsonObject message = event.getAsJsonObject().get("callback_query").getAsJsonObject().get("message").getAsJsonObject();
            String chat_id = message.get("chat").getAsJsonObject().get("id").getAsString();
            String first_name = message.get("chat").getAsJsonObject().get("first_name").getAsString();
            String last_name = message.get("chat").getAsJsonObject().get("last_name").getAsString();
            Chat chat = new Chat(chat_id, first_name, last_name);
            String payload = event.getAsJsonObject().get("callback_query").getAsJsonObject().get("data").getAsString();
            this.reactionCallback.setEvent(payload, chat);
            lastMessageTimestamp = timestamp;
        }
    }

    private void textParse(JsonElement event) {
        int timestamp = event.getAsJsonObject().get("message").getAsJsonObject().get("date").getAsInt();
        if(timestamp != lastMessageTimestamp) {
            System.out.println(event);
            JsonObject message = event.getAsJsonObject().get("message").getAsJsonObject();
            String chat_id = message.get("chat").getAsJsonObject().get("id").getAsString();
            String first_name = message.get("chat").getAsJsonObject().get("first_name").getAsString();
            String last_name = message.get("chat").getAsJsonObject().get("last_name").getAsString();
            String message_id = message.get("message_id").getAsString();
            int message_date = message.get("date").getAsInt();
            String text = message.get("text").getAsString();
            TextMessage text_message = new TextMessage(message_id, message_date, text);
            Chat chat = new Chat(chat_id, first_name, last_name);
            this.reaction.setEvent(text_message, chat);
            lastMessageTimestamp = timestamp;
        }
    }

    /**
     * Send text message
     * @param recipient String chat_id from telegram
     * @param text String text message
     */
    public static void send(String recipient, String text) {
        try{
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(addressWebhook + sendingMethod);
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("text", text));
            nvps.add(new BasicNameValuePair("chat_id", recipient));
            httppost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
            httpclient.execute(httppost);
        } catch(ClientProtocolException e) {
            System.out.println("Client protocol exception: ");
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Send InlineKeyboard markup
     * @param recipient String chat_id from telegram
     * @param text String text message which attached to keyboard markup
     * @param ik InlineKeyboard keyboard markup
     */
    public static void send(String recipient, String text, InlineKeyboard ik) {
        try{
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(addressWebhook + sendingMethod);
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("text", text));
            nvps.add(new BasicNameValuePair("chat_id", recipient));
            nvps.add(new BasicNameValuePair("reply_markup", ik.toJSON()));
            httppost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
            httpclient.execute(httppost);
        } catch(ClientProtocolException e) {
            System.out.println("Client protocol exception: ");
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Send an image to chat by URL
     * @param recipient String chat_id from telegram
     * @param url String image url
     */
    public static void sendPhoto(String recipient, String url) {
        try{
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(addressWebhook + sendPhotoMethod);
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("photo", url));
            nvps.add(new BasicNameValuePair("chat_id", recipient));
            httppost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
            httpclient.execute(httppost);
        } catch(ClientProtocolException e) {
            System.out.println("Client protocol exception: ");
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get response from other bot (ask.pannous.com service)
     * @param input String your question
     */
    public static String askForHelp(String input) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet("http://ask.pannous.com/api?input="+ URLEncoder.encode(input, "UTF-8") +"&locale=en_US");
            CloseableHttpResponse response = httpclient.execute(httpget);
            int data = response.getEntity().getContent().read();
            char content;
            String body = "";
            while(data != -1) {
                content = (char) data;
                body += content;
                data = response.getEntity().getContent().read();
            }
            JsonObject bodyData = TelegramBot.getJson(body);
            String answer = bodyData.get("output").getAsJsonArray().get(0).getAsJsonObject().get("actions").getAsJsonObject().get("say").getAsJsonObject().get("text").getAsString();
            httpclient.close();
            if(answer.length() > 1500) {
                return answer.substring(0, 1500) + "...";
            }
            return answer;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Sorry, i don't know...";
        }
    }

    /**
     * Get a JSON body, using a GSON lib
     * @param content JSON string
     */
    public static JsonObject getJson(String content) {
        JsonObject data = new JsonObject();
        Gson gson = new Gson();
        try {
            data = gson.fromJson(content, JsonObject.class);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            return data;
        }
    }
}
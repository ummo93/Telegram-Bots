package com.appartika.telegram;

public class Audio {

    private String url;
    private String caption = "";

    public Audio(String url) {
        this.url = url;
    }

    public Audio(String url, String caption) {
        this.url = url;
        this.caption = caption;
    }

    public String getUrl() {
        return this.url;
    }

    public String getCaption() {
        return this.caption;
    }

}

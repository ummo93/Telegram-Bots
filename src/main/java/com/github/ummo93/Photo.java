package com.github.ummo93;

public class Photo {

    private String url;
    private String caption = "";

    public Photo(String url, String caption) {
        this.url = url;
        this.caption = caption;
    }
    public Photo(String url) {
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

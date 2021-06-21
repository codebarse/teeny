package com.teeny.request;

public class CreateRequest {

    private String url;
    private String customKey;

    public CreateRequest() {
    }

    public CreateRequest(String url, String customKey) {
        this.url = url;
        this.customKey = customKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCustomKey() {
        return customKey;
    }

    public void setCustomKey(String customKey) {
        this.customKey = customKey;
    }
}

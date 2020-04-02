package com.teeny.request;

public class CreateRequest {

    private String url;

    public CreateRequest() {
    }

    public CreateRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

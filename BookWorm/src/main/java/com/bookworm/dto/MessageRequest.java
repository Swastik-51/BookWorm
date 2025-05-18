package com.bookworm.dto;

public class MessageRequest {
    private String content;
    private String type;

    public MessageRequest() {
    }

    public MessageRequest(String content, String type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
} 
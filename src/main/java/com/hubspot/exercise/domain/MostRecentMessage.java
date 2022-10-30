package com.hubspot.exercise.domain;

public class MostRecentMessage {
    private long userId;
    private String content;
    private long timestamp;

    protected MostRecentMessage() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public MostRecentMessage(Message message) {
        this.setContent(message.getContent());
        this.setTimestamp(message.getTimestamp());
        this.userId = message.getFromUserId();
    }
}

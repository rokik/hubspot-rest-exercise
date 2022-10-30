package com.hubspot.exercise.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Conversation {
    private String avatar;
    private String firstName;
    private String lastName;
    private MostRecentMessage mostRecentMessage;
    private long totalMessages = 0l;
    private long userId;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public MostRecentMessage getMostRecentMessage() {
        return mostRecentMessage;
    }

    public void setMostRecentMessage(MostRecentMessage mostRecentMessage) {
        this.mostRecentMessage = mostRecentMessage;
    }

    public long getTotalMessages() {
        return totalMessages;
    }

    public void setTotalMessages(long totalMessages) {
        this.totalMessages = totalMessages;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUser(User user) {
        this.setUserId(user.getId());
        this.setAvatar(user.getAvatar());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
    }
}

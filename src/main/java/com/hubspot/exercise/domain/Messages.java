package com.hubspot.exercise.domain;

import java.util.ArrayList;
import java.util.List;

public class Messages {
    private List<Message> messages = new ArrayList<Message>();
    private Integer userId;
    private List<User> users = new ArrayList<User>();

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
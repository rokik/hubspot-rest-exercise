package com.hubspot.exercise.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Conversations {
    private Collection<Conversation> conversations = new ArrayList<Conversation>();

    public Collection<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(Collection<Conversation> conversations) {
        this.conversations = conversations;
    }
}
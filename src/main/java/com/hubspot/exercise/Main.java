package com.hubspot.exercise;

import com.hubspot.exercise.domain.Conversations;
import com.hubspot.exercise.domain.Messages;
import com.hubspot.exercise.http.RestClient;
import com.hubspot.exercise.manager.TransformationManager;

public class Main {
    public static void main(String[] args) throws Exception{
        Messages messages = RestClient.getInstance().getMessages();
        Conversations conversations = TransformationManager.getInstance().transformToConversations(messages);
        RestClient.getInstance().postConversations(conversations);
    }
}

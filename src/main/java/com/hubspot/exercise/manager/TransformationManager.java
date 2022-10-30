package com.hubspot.exercise.manager;

import com.hubspot.exercise.domain.*;

import java.util.*;

public class TransformationManager {
    private static TransformationManager instance = new TransformationManager();

    protected TransformationManager() {
    }

    public static TransformationManager getInstance() {
        return instance;
    }

    public Conversations transformToConversations(Messages messages) {
        Map<Long, Conversation> conversationMap = deriveConversations(messages);

        TreeSet<Conversation> set = new TreeSet<>(
                (a,b) -> (int) (b.getMostRecentMessage().getTimestamp() - a.getMostRecentMessage().getTimestamp()));
        set.addAll(conversationMap.values());
        Conversations conversations = new Conversations();
        conversations.setConversations(new ArrayList<>(set));
        return conversations;
    }

    private Map<Long, User> buildUserLookupMap(Messages messages) {
        Map<Long, User> userMap = new HashMap<>();
        for (User user : messages.getUsers()) {
            userMap.put(user.getId(), user);
        }
        return userMap;
    }

    private  Map<Long, Conversation> deriveConversations(Messages messages) {
        Map<Long, User> userMap = buildUserLookupMap(messages);
        Map<Long, Conversation> conversationMap = new HashMap<>();

        for (Message message : messages.getMessages()) {
            if (message.getFromUserId() == messages.getUserId()
                    || message.getToUserId() == messages.getUserId()) {

                long subjectUserId = message.getFromUserId() ==
                        messages.getUserId() ? message.getToUserId() : message.getFromUserId();
                Conversation tempConversation = conversationMap.get(subjectUserId);

                if(tempConversation == null) {
                    tempConversation = new Conversation();
                    tempConversation.setUser(userMap.get(subjectUserId));
                    tempConversation.setMostRecentMessage(new MostRecentMessage(message));
                    conversationMap.put (subjectUserId, tempConversation);
                } else {
                    if (tempConversation.getMostRecentMessage().getTimestamp() < message.getTimestamp()) {
                        tempConversation.setMostRecentMessage(new MostRecentMessage(message));
                    }
                }

                tempConversation.setTotalMessages(tempConversation.getTotalMessages() + 1);

            }
        }

        return conversationMap;
    }
}

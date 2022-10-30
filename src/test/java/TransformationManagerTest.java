import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.exercise.domain.Conversation;
import com.hubspot.exercise.domain.Conversations;
import com.hubspot.exercise.domain.Messages;
import com.hubspot.exercise.manager.TransformationManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class TransformationManagerTest {
    private String incomingMessage = "{\n" +
            "        \"messages\": [\n" +
            "        {\n" +
            "            \"content\": \"The quick brown fox jumps over the lazy dog\",\n" +
            "                \"fromUserId\": 50210,\n" +
            "                \"timestamp\": 1529338342000,\n" +
            "                \"toUserId\": 67452\n" +
            "        },\n" +
            "        {\n" +
            "            \"content\": \"Pangrams originate in the discotheque\",\n" +
            "                \"fromUserId\": 67452,\n" +
            "                \"timestamp\": 1529075415000,\n" +
            "                \"toUserId\": 50210\n" +
            "        },\n" +
            "        {\n" +
            "            \"content\": \"Have you planned your holidays this year yet?\",\n" +
            "                \"fromUserId\": 67452,\n" +
            "                \"timestamp\": 1529542953000,\n" +
            "                \"toUserId\": 50210\n" +
            "        },\n" +
            "        {\n" +
            "            \"content\": \"Strange noises have been heard on the moors\",\n" +
            "                \"fromUserId\": 78596,\n" +
            "                \"timestamp\": 1533112961000,\n" +
            "                \"toUserId\": 50210\n" +
            "        },\n" +
            "        {\n" +
            "            \"content\": \"You go straight ahead for two hundred yards and then take the first right turn\",\n" +
            "                \"fromUserId\": 50210,\n" +
            "                \"timestamp\": 1533197225000,\n" +
            "                \"toUserId\": 78596\n" +
            "        },\n" +
            "        {\n" +
            "            \"content\": \"It's a privilege and an honour to have known you\",\n" +
            "                \"fromUserId\": 78596,\n" +
            "                \"timestamp\": 1533118270000,\n" +
            "                \"toUserId\": 50210\n" +
            "        }\n" +
            "    ],\n" +
            "        \"userId\": 50210,\n" +
            "            \"users\": [\n" +
            "        {\n" +
            "            \"avatar\": \"octocat.jpg\",\n" +
            "                \"firstName\": \"John\",\n" +
            "                \"lastName\": \"Doe\",\n" +
            "                \"id\": 67452\n" +
            "        },\n" +
            "        {\n" +
            "            \"avatar\": \"genie.png\",\n" +
            "                \"firstName\": \"Michael\",\n" +
            "                \"lastName\": \"Crowley\",\n" +
            "                \"id\": 78596\n" +
            "        }\n" +
            "    ]\n" +
            "    }";

    private String expectedTransformedMessage = "{\n" +
            "    \"conversations\": [\n" +
            "        {\n" +
            "            \"avatar\": \"genie.png\",\n" +
            "            \"firstName\": \"Michael\",\n" +
            "            \"lastName\": \"Crowley\",\n" +
            "            \"mostRecentMessage\": {\n" +
            "                \"content\": \"You go straight ahead for two hundred yards and then take the first right turn\",\n" +
            "                \"timestamp\": 1533197225000,\n" +
            "                \"userId\": 50210\n" +
            "            },\n" +
            "            \"totalMessages\": 3,\n" +
            "            \"userId\": 78596\n" +
            "        },\n" +
            "        {\n" +
            "            \"avatar\": \"octocat.jpg\",\n" +
            "            \"firstName\": \"John\",\n" +
            "            \"lastName\": \"Doe\",\n" +
            "            \"mostRecentMessage\": {\n" +
            "                \"content\": \"Have you planned your holidays this year yet?\",\n" +
            "                \"timestamp\": 1529542953000,\n" +
            "                \"userId\": 67452\n" +
            "            },\n" +
            "            \"totalMessages\": 3,\n" +
            "            \"userId\": 67452\n" +
            "      }\n" +
            "    ]\n" +
            "}";

    private Messages messages;
    private Conversations conversations;
    private ObjectMapper mapper;

    private TransformationManager manager;

    @BeforeEach
    public void setup() throws Exception {
        mapper = new ObjectMapper();
        messages = mapper.readValue(incomingMessage, Messages.class);
        conversations = mapper.readValue(expectedTransformedMessage, Conversations.class);
        manager = TransformationManager.getInstance();
    }

    @Test
    public void testMessages() throws Exception {
        Assertions.assertNotNull(messages.getMessages());
        Assertions.assertTrue(messages.getMessages().size() > 0);
    }

    @Test
    public void testConversations() throws  Exception {
        Assertions.assertNotNull(conversations.getConversations());
        Assertions.assertTrue(conversations.getConversations().size() > 0);
    }

    @Test
    public void testTransformation() {
        Conversations resultConversations = manager.transformToConversations(messages);

        Assertions.assertNotNull(resultConversations);
        Assertions.assertNotNull(resultConversations.getConversations());

        Assertions.assertEquals(2, resultConversations.getConversations().size());

        for (Conversation resultConversation :  resultConversations.getConversations()) {
            Assertions.assertEquals(3, resultConversation.getTotalMessages());
        }

        Iterator<Conversation> resultConversationsIter = resultConversations.getConversations().iterator();
        for (Conversation expected : conversations.getConversations()) {
            Conversation result = resultConversationsIter.next();

            Assertions.assertEquals(expected.getAvatar(), result.getAvatar());
            Assertions.assertEquals(expected.getFirstName(), result.getFirstName());
            Assertions.assertEquals(expected.getLastName(), result.getLastName());
            Assertions.assertEquals(expected.getMostRecentMessage().getTimestamp(), result.getMostRecentMessage().getTimestamp());
            Assertions.assertEquals(expected.getMostRecentMessage().getContent(), result.getMostRecentMessage().getContent());
        }
    }
}

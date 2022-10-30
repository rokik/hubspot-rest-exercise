package com.hubspot.exercise.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.exercise.config.ApiConfig;
import com.hubspot.exercise.domain.Conversations;
import com.hubspot.exercise.domain.Messages;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RestClient {
    private ObjectMapper mapper;
    private DefaultHttpClient httpClient;
    private static RestClient restClient = new RestClient();

    protected RestClient() {
        this.mapper = new ObjectMapper();
        this.httpClient = new DefaultHttpClient();
    }

    public static RestClient getInstance() {
        return restClient;
    }

    public String appendApiKeyToUrl(String url) {
        return url + "?" + ApiConfig.API_KEY_HTTP_PARAM;
    }

    public Messages getMessages() throws IOException {
        HttpGet getRequest = new HttpGet(appendApiKeyToUrl(ApiConfig.messagesUrl));
        getRequest.addHeader("accept", "application/json");

        HttpResponse response = httpClient.execute(getRequest);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("HTTP error code : " + response.getStatusLine().getStatusCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

        StringBuilder responseBuilder = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            responseBuilder.append(line);
        }
        return mapper.readValue(responseBuilder.toString(), Messages.class);
    }

    public void postConversations (Conversations conversations) throws Exception {
        HttpPost postRequest = new HttpPost(appendApiKeyToUrl(ApiConfig.conversationsUrl));

        StringEntity input = new StringEntity(mapper.writeValueAsString(conversations));
        input.setContentType("application/json");
        postRequest.setEntity(input);

        HttpResponse response = httpClient.execute(postRequest);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("HTTP error code : " + response.getStatusLine().getStatusCode());
        }
    }
}

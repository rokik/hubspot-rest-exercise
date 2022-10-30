package com.hubspot.exercise.config;

public interface ApiConfig {
    String API_KEY = "1699b902ac390d3e081f868998de";
    String BASE_URL = "https://candidate.hubteam.com/candidateTest/v3/problem";
    String messagesUrl = BASE_URL + "/dataset";
    String conversationsUrl = BASE_URL + "/result";
    String API_KEY_HTTP_PARAM = "userKey=" + API_KEY;
}

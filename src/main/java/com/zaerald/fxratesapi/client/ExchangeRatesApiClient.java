package com.zaerald.fxratesapi.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ExchangeRatesApiClient {

    @Value("${foreign-exchange.provider.url}")
    private String baseUrl;

    @Value("${foreign-exchange.provider.accessKey}")
    private String accessKey;

    private final ObjectMapper objectMapper;

    public JsonNode getEntity(String path) throws JsonProcessingException {
        var restTemplate = new RestTemplate();

        var responseBody = restTemplate.getForObject(
            baseUrl + path + "?access_key=" + accessKey,
            String.class);

        return objectMapper.readTree(responseBody);
    }

}

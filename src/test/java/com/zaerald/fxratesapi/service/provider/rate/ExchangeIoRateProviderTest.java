package com.zaerald.fxratesapi.service.provider.rate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaerald.fxratesapi.client.ExchangeRatesApiClient;
import com.zaerald.fxratesapi.exception.InvalidRateValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ExchangeIoRateProviderTest {

    @Mock
    private ExchangeRatesApiClient exchangeRatesApiClient;

    @InjectMocks
    private ExchangeIoRateProvider rateProvider;

    @Test
    @DisplayName("GIVEN baseCurrency AND targetCurrency AND client has a response THEN properly derive rate")
    void testRate() throws JsonProcessingException {
        String base = "USD";
        String target = "PHP";

        Map<String, Object> root = new HashMap<>();
        root.put("base", "EUR");
        root.put("rates", mockedRates());

        var mapper = new ObjectMapper();
        JsonNode rootNode = mapper.valueToTree(root);

        given(exchangeRatesApiClient.getEntity("/latest"))
            .willReturn(rootNode);

        double rate = rateProvider.getRate(base, target);

        assertThat(rate).isEqualTo(1.5);
    }

    @Test
    @DisplayName("GIVEN baseCurrency AND targetCurrency AND client has provided an invalid rate THEN throw an exception")
    void testRateWithInvalidRate() throws JsonProcessingException {
        String base = "INVALID";
        String target = "PHP";

        Map<String, Object> root = new HashMap<>();
        root.put("base", "EUR");
        root.put("rates", mockedRates());

        var mapper = new ObjectMapper();
        JsonNode rootNode = mapper.valueToTree(root);

        given(exchangeRatesApiClient.getEntity("/latest"))
            .willReturn(rootNode);

        assertThatThrownBy(() -> rateProvider.getRate(base, target))
            .isInstanceOfSatisfying(InvalidRateValueException.class, it ->
                assertThat(it.getMessage()).contains("INVALID")
            );
    }

    private Map<String, Double> mockedRates() {
        return Map.of(
            "AED", 20d,
            "USD", 40d,
            "PHP", 60d,

            "INVALID", 0d
        );
    }

}
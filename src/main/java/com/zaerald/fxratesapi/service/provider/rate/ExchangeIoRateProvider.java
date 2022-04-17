package com.zaerald.fxratesapi.service.provider.rate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.zaerald.fxratesapi.client.ExchangeRatesApiClient;
import com.zaerald.fxratesapi.exception.InvalidRateValueException;
import com.zaerald.fxratesapi.exception.RateNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor(staticName = "newInstance")
public class ExchangeIoRateProvider implements RateProvider {

    private final ExchangeRatesApiClient exchangeRatesApiClient;

    @Override
    public double getRate(String baseCurrency, String targetCurrency) throws RateNotFoundException {
        try {
            JsonNode jsonNode = exchangeRatesApiClient.getEntity("/latest");
            JsonNode baseNode = jsonNode.get("base");
            String responseBaseCode = baseNode.textValue();

            JsonNode ratesNode = jsonNode.get("rates");
            Iterator<Map.Entry<String, JsonNode>> ratesFieldIterator = ratesNode.fields();
            Map<String, Double> ratesMapping = new HashMap<>();
            while (ratesFieldIterator.hasNext()) {
                Map.Entry<String, JsonNode> next = ratesFieldIterator.next();
                ratesMapping.put(next.getKey(), next.getValue().doubleValue());
            }

            if (baseCurrency.equals(responseBaseCode)) {
                return ratesMapping.get(targetCurrency);
            }

            double baseRateValue = ratesMapping.get(baseCurrency);
            double targetRateValue = ratesMapping.get(targetCurrency);
            if (baseRateValue == 0) {
                throw new InvalidRateValueException(baseCurrency, baseRateValue);
            }

            return targetRateValue / baseRateValue;
        } catch (JsonProcessingException e) {
            log.error("Failed to provide " + baseCurrency + "-" + targetCurrency + " rate", e);
        }

        throw new RateNotFoundException(baseCurrency, targetCurrency);
    }

}

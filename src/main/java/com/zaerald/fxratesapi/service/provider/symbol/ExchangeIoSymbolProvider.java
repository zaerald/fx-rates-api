package com.zaerald.fxratesapi.service.provider.symbol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.zaerald.fxratesapi.client.ExchangeRatesApiClient;
import com.zaerald.fxratesapi.model.Symbol;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor(staticName = "newInstance")
public class ExchangeIoSymbolProvider implements SymbolProvider {

    private final List<Symbol> symbols = new ArrayList<>();

    private final ExchangeRatesApiClient exchangeRatesApiClient;

    @Override
    public List<Symbol> getCurrencySymbols() {
        if (!symbols.isEmpty()) return symbols;

        try {
            JsonNode jsonNode = exchangeRatesApiClient.getEntity("/symbols");
            JsonNode ratesNode = jsonNode.get("symbols");
            Iterator<Map.Entry<String, JsonNode>> symbolsFieldIterator = ratesNode.fields();

            while (symbolsFieldIterator.hasNext()) {
                Map.Entry<String, JsonNode> next = symbolsFieldIterator.next();
                symbols.add(Symbol.of(next.getKey(), next.getValue().textValue()));
            }

            return symbols;
        } catch (JsonProcessingException e) {
            log.error("Unable to retrieve the currency symbols.", e);
        }

        return Collections.emptyList();
    }

}

package com.zaerald.fxratesapi.service.provider.rate;

import com.zaerald.fxratesapi.exception.RateNotFoundException;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(staticName = "newInstance")
public final class FakeRateProvider implements RateProvider {

    private static final String SEPARATOR = "-";

    private Map<String, Double> ratesMapping = new HashMap<>();

    @Override
    public double getRate(String baseCurrency, String targetCurrency) throws RateNotFoundException {
        if (ratesMapping.isEmpty()) {
            ratesMapping = generateRates();
        }

        Double rate = ratesMapping.get(String.join(SEPARATOR, baseCurrency, targetCurrency));
        if (rate == null)
            throw new RateNotFoundException(baseCurrency, targetCurrency);
        return rate;
    }

    private Map<String, Double> generateRates() {
        return Map.of(
            "USD-PHP", 52.17,
            "PHP-USD", 0.019,
            "AED-JPY", 93.49,
            "JPY-AED", 0.011,
            "EUR-GBP", 0.83,
            "GBP-EUR", 1.21
        );
    }

}

package com.zaerald.fxratesapi.service.provider.rate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultRateProviderTest {

    private final DefaultRateProvider defaultRateProvider = new DefaultRateProvider();

    @ParameterizedTest
    @CsvFileSource(resources = "/fakeRateProvider.csv", numLinesToSkip = 1)
    @DisplayName("GIVEN baseCurrency AND targetCurrency THEN provide the rate")
    void testGetRateProvider(double expectedRate, String baseCurrency, String targetCurrency) {

        double rate = defaultRateProvider.getRate(baseCurrency, targetCurrency);

        assertThat(rate).isEqualTo(expectedRate);
    }

}
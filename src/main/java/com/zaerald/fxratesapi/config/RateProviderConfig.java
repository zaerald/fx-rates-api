package com.zaerald.fxratesapi.config;

import com.zaerald.fxratesapi.client.ExchangeRatesApiClient;
import com.zaerald.fxratesapi.service.provider.rate.ExchangeIoRateProvider;
import com.zaerald.fxratesapi.service.provider.rate.FakeRateProvider;
import com.zaerald.fxratesapi.service.provider.rate.RateProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateProviderConfig {

    @Bean
    @ConditionalOnProperty(name = "foreign-exchange.provider.service", havingValue = "fake")
    public RateProvider fakeRateProvider() {
        return FakeRateProvider.newInstance();
    }

    @Bean
    @ConditionalOnMissingBean(RateProvider.class)
    public RateProvider exchangeIoRateProvider(ExchangeRatesApiClient exchangeRatesApiClient) {
        return ExchangeIoRateProvider.newInstance(exchangeRatesApiClient);
    }

}

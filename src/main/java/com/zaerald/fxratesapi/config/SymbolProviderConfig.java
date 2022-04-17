package com.zaerald.fxratesapi.config;

import com.zaerald.fxratesapi.client.ExchangeRatesApiClient;
import com.zaerald.fxratesapi.service.provider.symbol.ExchangeIoSymbolProvider;
import com.zaerald.fxratesapi.service.provider.symbol.FakeSymbolProvider;
import com.zaerald.fxratesapi.service.provider.symbol.SymbolProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SymbolProviderConfig {

    @Bean
    @ConditionalOnProperty(name = "foreign-exchange.provider.service", havingValue = "fake")
    public SymbolProvider fakeSymbolProvider() {
        return FakeSymbolProvider.newInstance();
    }

    @Bean
    @ConditionalOnMissingBean(SymbolProvider.class)
    public SymbolProvider exchangeIoSymbolProvider(ExchangeRatesApiClient exchangeRatesApiClient) {
        return ExchangeIoSymbolProvider.newInstance(exchangeRatesApiClient);
    }

}

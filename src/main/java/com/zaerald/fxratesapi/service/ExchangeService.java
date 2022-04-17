package com.zaerald.fxratesapi.service;

import com.zaerald.fxratesapi.dto.ExchangeDto;
import com.zaerald.fxratesapi.exception.UnsupportedSymbolException;
import com.zaerald.fxratesapi.model.Symbol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final CurrencySymbolService currencySymbolService;

    private final RateProviderService rateProviderService;

    public ExchangeDto exchange(String baseCurrency, String targetCurrency, Double amount) {
        Symbol baseSymbol = currencySymbolService.from(baseCurrency)
            .orElseThrow(() -> new UnsupportedSymbolException(baseCurrency));
        Symbol targetSymbol = currencySymbolService.from(targetCurrency)
            .orElseThrow(() -> new UnsupportedSymbolException(targetCurrency));

        double rate = rateProviderService.getRate(baseSymbol, targetSymbol);

        return ExchangeDto.builder()
            .baseCurrency(baseSymbol)
            .targetCurrency(targetSymbol)
            .baseAmount(amount)
            .rate(rate)
            .value(BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(rate)))
            .timestamp(ZonedDateTime.now())
            .build();
    }

}

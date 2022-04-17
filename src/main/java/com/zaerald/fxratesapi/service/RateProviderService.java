package com.zaerald.fxratesapi.service;

import com.zaerald.fxratesapi.exception.NoRateFoundException;
import com.zaerald.fxratesapi.model.Symbol;
import com.zaerald.fxratesapi.service.provider.rate.RateProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateProviderService {

    private final RateProvider rateProvider;

    public double getRate(Symbol baseCurrency, Symbol targetCurrency) throws NoRateFoundException {
        return rateProvider.getRate(baseCurrency.getCode(), targetCurrency.getCode());
    }

}

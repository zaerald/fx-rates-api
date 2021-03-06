package com.zaerald.fxratesapi.service;

import com.zaerald.fxratesapi.exception.RateNotFoundException;
import com.zaerald.fxratesapi.model.Symbol;
import com.zaerald.fxratesapi.service.provider.rate.RateProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateProviderService {

    private final RateProvider rateProvider;

    public double getRate(Symbol baseCurrency, Symbol targetCurrency) throws RateNotFoundException {
        return rateProvider.getRate(baseCurrency.getCode(), targetCurrency.getCode());
    }

}

package com.zaerald.fxratesapi.service;

import com.zaerald.fxratesapi.exception.NoRateFoundException;
import com.zaerald.fxratesapi.model.Symbol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateProviderService {

    public double getRate(Symbol baseCurrency, Symbol targetCurrency) throws NoRateFoundException {
        return -1d;
    }

}

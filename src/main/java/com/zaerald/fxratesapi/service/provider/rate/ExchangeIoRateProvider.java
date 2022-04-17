package com.zaerald.fxratesapi.service.provider.rate;

import com.zaerald.fxratesapi.exception.RateNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "newInstance")
public class ExchangeIoRateProvider implements RateProvider {

    @Override
    public double getRate(String baseCurrency, String targetCurrency) throws RateNotFoundException {
        throw new RateNotFoundException(baseCurrency, targetCurrency);
    }

}

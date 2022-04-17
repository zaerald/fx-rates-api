package com.zaerald.fxratesapi.service.provider.rate;

import com.zaerald.fxratesapi.exception.RateNotFoundException;

public interface RateProvider {

    double getRate(String baseCurrency, String targetCurrency) throws RateNotFoundException;

}

package com.zaerald.fxratesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public final class RateNotFoundException extends RuntimeException {

    public RateNotFoundException(String baseCurrency, String targetCurrency) {
        super("No rate conversion found for '" + baseCurrency + "' to '" + targetCurrency + "'");
    }

}

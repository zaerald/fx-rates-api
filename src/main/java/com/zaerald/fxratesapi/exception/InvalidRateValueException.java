package com.zaerald.fxratesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class InvalidRateValueException extends RuntimeException {

    public InvalidRateValueException(String symbol, double rate) {
        super("Invalid rate value provided with symbol code '" + symbol + "' with a rate of '" + rate + "'");
    }

}

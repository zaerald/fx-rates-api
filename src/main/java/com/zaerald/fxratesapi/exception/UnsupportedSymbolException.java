package com.zaerald.fxratesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class UnsupportedSymbolException extends RuntimeException {

    public UnsupportedSymbolException(String code) {
        super("Provided symbol is not supported: '" + code + "'");
    }

}

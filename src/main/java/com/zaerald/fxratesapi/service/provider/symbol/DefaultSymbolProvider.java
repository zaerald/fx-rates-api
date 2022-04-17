package com.zaerald.fxratesapi.service.provider.symbol;

import com.zaerald.fxratesapi.model.Symbol;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultSymbolProvider implements SymbolProvider {

    @Override
    public List<Symbol> getCurrencySymbols() {
        return List.of(
            Symbol.of("USD", "United States dollar"),
            Symbol.of("PHP", "Philippine peso"),
            Symbol.of("AED", "United Arab Emirates dirham"),
            Symbol.of("JPY", "Japanese yen"),
            Symbol.of("EUR", "Euro"),
            Symbol.of("GBP", "British pound")
        );
    }

}

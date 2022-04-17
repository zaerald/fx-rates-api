package com.zaerald.fxratesapi.service;

import com.zaerald.fxratesapi.model.Symbol;
import com.zaerald.fxratesapi.service.provider.symbol.SymbolProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencySymbolService {

    private final SymbolProvider symbolProvider;

    public List<Symbol> getCurrencySymbols() {
        return symbolProvider.getCurrencySymbols();
    }

    public Optional<Symbol> from(String code) {
        return getCurrencySymbols().stream()
            .filter(it -> it.getCode().equals(code))
            .findFirst();
    }

}

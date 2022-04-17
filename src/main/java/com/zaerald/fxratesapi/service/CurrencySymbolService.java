package com.zaerald.fxratesapi.service;

import com.zaerald.fxratesapi.model.Symbol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencySymbolService {

    public List<Symbol> getCurrencySymbols() {
        return Collections.emptyList();
    }

    public Optional<Symbol> from(String code) {
        return Optional.empty();
    }

}

package com.zaerald.fxratesapi.service.provider.symbol;

import com.zaerald.fxratesapi.model.Symbol;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor(staticName = "newInstance")
public class ExchangeIoSymbolProvider implements SymbolProvider {

    @Override
    public List<Symbol> getCurrencySymbols() {
        return Collections.emptyList();
    }

}

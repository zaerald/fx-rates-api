package com.zaerald.fxratesapi.service.provider.symbol;

import com.zaerald.fxratesapi.model.Symbol;

import java.util.List;

public interface SymbolProvider {

    List<Symbol> getCurrencySymbols();

}

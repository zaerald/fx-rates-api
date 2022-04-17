package com.zaerald.fxratesapi.controller;

import com.zaerald.fxratesapi.model.Symbol;
import com.zaerald.fxratesapi.service.CurrencySymbolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/symbols")
@RequiredArgsConstructor
public class CurrencySymbolController {

    private final CurrencySymbolService currencySymbolService;

    @GetMapping
    public List<Symbol> getCurrencySymbols() {
        return currencySymbolService.getCurrencySymbols();
    }

}

package com.zaerald.fxratesapi.service;

import com.zaerald.fxratesapi.model.Symbol;
import com.zaerald.fxratesapi.service.provider.symbol.SymbolProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CurrencySymbolServiceTest {

    @Mock
    private SymbolProvider symbolProvider;

    @InjectMocks
    private CurrencySymbolService currencySymbolService;

    @Test
    @DisplayName("GIVEN a list of provided currency symbols THEN return the exact list")
    void testGetCurrencySymbols() {
        List<Symbol> symbols = List.of(
            Symbol.of("TEST", "some test symbol"),
            Symbol.of("ABC", "Alpha Beta Charlie"),
            Symbol.of("XYZ", "X-Ray Yankee Zulu")
        );

        given(symbolProvider.getCurrencySymbols())
            .willReturn(symbols);

        List<Symbol> currencySymbols = currencySymbolService.getCurrencySymbols();

        assertThat(currencySymbols)
            .isNotNull()
            .isNotEmpty()
            .hasSize(3);
    }

    @Test
    @DisplayName("GIVEN a symbol code AND a symbol with exists THEN return return the symbol")
    void testGetOneExistingSymbol() {
        given(symbolProvider.getCurrencySymbols())
            .willReturn(List.of(Symbol.of("EXISTS", "Mocked existing symbol")));

        Optional<Symbol> symbol = currencySymbolService.from("EXISTS");
        assertThat(symbol).isPresent();
    }

    @Test
    @DisplayName("GIVEN a symbol code AND a symbol with exists THEN return return the symbol")
    void testGetSymbolThatDoesNotExist() {
        given(symbolProvider.getCurrencySymbols())
            .willReturn(List.of(Symbol.of("JKL", "Juliet Kilo Lima")));

        Optional<Symbol> symbol = currencySymbolService.from("DOES_NOT_EXIST");
        assertThat(symbol).isEmpty();
    }

}
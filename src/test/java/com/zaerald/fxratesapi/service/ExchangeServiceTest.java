package com.zaerald.fxratesapi.service;

import com.zaerald.fxratesapi.dto.ExchangeDto;
import com.zaerald.fxratesapi.exception.NoRateFoundException;
import com.zaerald.fxratesapi.exception.UnsupportedSymbolException;
import com.zaerald.fxratesapi.model.Symbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceTest {

    @Mock
    private CurrencySymbolService currencySymbolService;

    @Mock
    private RateProviderService rateProviderService;

    @InjectMocks
    private ExchangeService exchangeService;

    @Captor
    private ArgumentCaptor<Symbol> baseSymbolArgumentCaptor;

    @Captor
    private ArgumentCaptor<Symbol> targetSymbolArgumentCaptor;

    @ParameterizedTest
    @CsvFileSource(resources = "/currencyExchange.csv", numLinesToSkip = 1)
    @DisplayName("GIVEN valid exchange symbols AND has rates available THEN properly compute for the exchange value")
    void testExchange(double expectedValue, String base, String target, double amount, double rate) {
        Symbol mockBase = Symbol.of(base, "Mock base");
        Symbol mockTarget = Symbol.of(target, "Mock target");

        given(currencySymbolService.from(base))
            .willReturn(Optional.of(mockBase));

        given(currencySymbolService.from(target))
            .willReturn(Optional.of(mockTarget));

        given(rateProviderService.getRate(mockBase, mockTarget))
            .willReturn(rate);

        ExchangeDto exchange = exchangeService.exchange(base, target, amount);

        verify(rateProviderService).getRate(baseSymbolArgumentCaptor.capture(), targetSymbolArgumentCaptor.capture());
        Symbol baseSymbol = baseSymbolArgumentCaptor.getValue();
        assertThat(baseSymbol).isNotNull()
            .extracting(Symbol::getCode)
            .isEqualTo(base);

        Symbol targetSymbol = targetSymbolArgumentCaptor.getValue();
        assertThat(targetSymbol).isNotNull()
            .extracting(Symbol::getCode)
            .isEqualTo(target);

        assertThat(exchange).isNotNull()
            .extracting(ExchangeDto::getValue)
            .usingComparator(BigDecimal::compareTo)
            .isEqualTo(BigDecimal.valueOf(expectedValue));
    }

    @Test
    @DisplayName("GIVEN unsupported base currency THEN throw an exception")
    void testExchangeWithInvalidBaseCurrency() {

        String base = "UNSUPPORTED";
        String target = "MOCK";

        assertThatThrownBy(() -> exchangeService.exchange(base, target, 10d))
            .isInstanceOfSatisfying(UnsupportedSymbolException.class, it ->
                assertThat(it.getMessage()).contains("UNSUPPORTED")
            );

        verifyNoInteractions(rateProviderService);
    }

    @Test
    @DisplayName("GIVEN unsupported target currency THEN throw an exception")
    void testExchangeWithInvalidTargetCurrency() {

        String base = "MOCK";
        String target = "UNSUPPORTED";

        Symbol mockBase = Symbol.of(base, "Mock base");

        given(currencySymbolService.from(base))
            .willReturn(Optional.of(mockBase));

        given(currencySymbolService.from(target))
            .willReturn(Optional.empty());

        assertThatThrownBy(() -> exchangeService.exchange(base, target, 10d))
            .isInstanceOfSatisfying(UnsupportedSymbolException.class, it ->
                assertThat(it.getMessage()).contains("UNSUPPORTED")
            );

        verifyNoInteractions(rateProviderService);
    }

    @Test
    @DisplayName("GIVEN base AND target currency AND no rate was found THEN throw an exception")
    void testExchangeWithUnknownRate() {

        Symbol usd = Symbol.of("USD", "United States Dollar");
        Symbol php = Symbol.of("PHP", "Philippine peso");

        given(currencySymbolService.from("USD"))
            .willReturn(Optional.of(usd));

        given(currencySymbolService.from("PHP"))
            .willReturn(Optional.of(php));

        given(rateProviderService.getRate(usd, php))
            .willThrow(new NoRateFoundException(usd.getCode(), php.getCode()));

        assertThatThrownBy(() -> exchangeService.exchange("USD", "PHP", 10d))
            .isInstanceOfSatisfying(NoRateFoundException.class, it ->
                assertThat(it.getMessage())
                    .contains("USD")
                    .contains("PHP")
            );
    }
}

package com.zaerald.fxratesapi.service;

import com.zaerald.fxratesapi.dto.ExchangeDto;
import com.zaerald.fxratesapi.model.Symbol;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Service
public class ExchangeService {

    public ExchangeDto exchange(String baseCurrency, String targetCurrency, Double amount) {
        return ExchangeDto.builder()
            .baseCurrency(Symbol.of("USD", "United States Dollar"))
            .targetCurrency(Symbol.of("PHP", "Philippine peso"))
            .baseAmount(0d)
            .rate(0d)
            .value(BigDecimal.ZERO)
            .timestamp(ZonedDateTime.now())
            .build();
    }

}

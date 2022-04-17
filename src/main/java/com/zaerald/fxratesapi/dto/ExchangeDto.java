package com.zaerald.fxratesapi.dto;

import com.zaerald.fxratesapi.model.Symbol;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class ExchangeDto {

    private Symbol baseCurrency;

    private Symbol targetCurrency;

    private Double baseAmount;

    private Double rate;

    private BigDecimal value;

    private ZonedDateTime timestamp;

}

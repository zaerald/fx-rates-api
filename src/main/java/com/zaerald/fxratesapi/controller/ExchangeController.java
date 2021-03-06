package com.zaerald.fxratesapi.controller;

import com.zaerald.fxratesapi.dto.ExchangeDto;
import com.zaerald.fxratesapi.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ExchangeDto exchange(
        @RequestParam("base") @NotBlank String baseCurrency,
        @RequestParam("target") @NotBlank String targetCurrency,
        @RequestParam @NotNull @Min(1) Double amount) {

        return exchangeService.exchange(baseCurrency, targetCurrency, amount);
    }

}

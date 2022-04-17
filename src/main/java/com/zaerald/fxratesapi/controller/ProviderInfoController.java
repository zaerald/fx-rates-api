package com.zaerald.fxratesapi.controller;

import com.zaerald.fxratesapi.model.ProviderInfo;
import com.zaerald.fxratesapi.service.ProviderInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/provider")
@RequiredArgsConstructor
public class ProviderInfoController {

    private final ProviderInfoService providerInfoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProviderInfo getProvider() {
        return providerInfoService.getCurrentProvider();
    }

}

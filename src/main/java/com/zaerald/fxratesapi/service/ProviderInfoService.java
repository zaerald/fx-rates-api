package com.zaerald.fxratesapi.service;

import com.zaerald.fxratesapi.model.ProviderInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProviderInfoService {

    @Value("${foreign-exchange.provider.service:exchangeratesapi.io}")
    private String provider;

    public ProviderInfo getCurrentProvider() {
        return ProviderInfo.of(provider);
    }

}

package com.zaerald.fxratesapi.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class ProviderInfo {

    private final String provider;

}

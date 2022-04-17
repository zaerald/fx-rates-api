package com.zaerald.fxratesapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class Symbol {

    private final String code;

    private final String description;

}

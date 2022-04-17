package com.zaerald.fxratesapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}

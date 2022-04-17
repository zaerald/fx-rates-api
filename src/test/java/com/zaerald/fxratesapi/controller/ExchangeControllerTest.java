package com.zaerald.fxratesapi.controller;

import com.zaerald.fxratesapi.exception.RateNotFoundException;
import com.zaerald.fxratesapi.service.ExchangeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ExchangeController.class)
class ExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeService exchangeService;

    @Test
    @DisplayName("GIVEN valid parameters THEN properly exchange")
    void testExchange() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/exchange")
            .param("base", "PHP")
            .param("target", "USD")
            .param("amount", "123")
        ).andExpect(status().isOk());

        verify(exchangeService).exchange("PHP", "USD", 123d);
    }

    @Test
    @DisplayName("GIVEN base is not present THEN do not exchange")
    void testExchangeInvalidBaseCurrency() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/exchange")
            .param("target", "USD")
            .param("amount", "123")
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(exchangeService);
    }

    @Test
    @DisplayName("GIVEN target is not present THEN do not exchange")
    void testExchangeInvalidTargetCurrency() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/exchange")
            .param("base", "PHP")
            .param("amount", "123")
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(exchangeService);
    }

    @Test
    @DisplayName("GIVEN amount is not present THEN do not exchange")
    void testExchangeInvalidAmount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/exchange")
            .param("base", "PHP")
            .param("target", "USD")
            .param("amount", "invalid amount")
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(exchangeService);
    }

    @Test
    @DisplayName("GIVEN rate is not found THEN throw an exception")
    void testExchangeRateNotFound() throws Exception {
        String base = "PHP";
        String target = "USD";

        given(exchangeService.exchange(base, target, 123d))
            .willThrow(new RateNotFoundException(base, target));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/exchange")
            .param("base", base)
            .param("target", target)
            .param("amount", "123")
        ).andExpect(status().isNotFound());
    }

}
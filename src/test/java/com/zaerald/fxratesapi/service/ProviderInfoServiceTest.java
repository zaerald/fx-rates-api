package com.zaerald.fxratesapi.service;

import com.zaerald.fxratesapi.model.ProviderInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PropertySourcesPlaceholderConfigurer.class)
@TestPropertySource(properties = "foreign-exchange.provider.service=fake")
@SpringBootTest(classes = ProviderInfoService.class)
class ProviderInfoServiceTest {

    @Autowired
    private ProviderInfoService providerInfoService;

    @Test
    @DisplayName("GIVEN provider properties THEN share the info")
    void testProviderInfo() {
        ProviderInfo currentProvider = providerInfoService.getCurrentProvider();

        assertThat(currentProvider).isNotNull()
            .extracting(ProviderInfo::getProvider)
            .isEqualTo("fake");
    }

}
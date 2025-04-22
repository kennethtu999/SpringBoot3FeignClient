package com.example.feignconsumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/consumer")
@RequiredArgsConstructor
public class ConsumerController {

    private final ProviderClient providerClient;

    @GetMapping("/test1")
    public String testWithValues() {
        return providerClient.testEndpoint("header-value", "param-value");
    }

    @GetMapping("/test2")
    public String testWithNullValues() {
        return providerClient.testEndpoint(null, null);
    }

    @GetMapping("/test3")
    public String testWithEmptyValues() {
        return providerClient.testEndpoint("", "");
    }

    @GetMapping("/test4")
    public String testHeaderHaveValueAndParamEmptyValue() {
        return providerClient.testEndpoint("header-value", "");
    }
} 
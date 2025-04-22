package com.example.feignconsumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "provider", url = "http://localhost:8081")
public interface ProviderClient {
    
    @GetMapping("/api/test")
    String testEndpoint(
            @RequestHeader(value = "test-header", required = false) String testHeader,
            @RequestParam(value = "test-param", required = false) String testParam);
} 
package com.example.feignprovider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProviderController {

    @GetMapping("/test")
    public String testEndpoint(
            @RequestHeader(value = "test-header", required = true) String testHeader,
            @RequestParam(value = "test-param", required = true) String testParam) {
        
        log.info("Received request with header: {}, param: {}", testHeader, testParam);
        return String.format("Header: %s, Param: %s", testHeader, testParam);
    }

    // @GetMapping("/test")
    // public String testEndpoint(
    //         @RequestHeader(value = "test-header", required = false) String testHeader,
    //         @RequestParam(value = "test-param", required = false) String testParam) {
        
    //     log.info("Received request with header: {}, param: {}", testHeader, testParam);
    //     return String.format("Header: %s, Param: %s", testHeader, testParam);
    // }
} 
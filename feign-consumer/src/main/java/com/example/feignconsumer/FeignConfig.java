package com.example.feignconsumer;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.Client;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class FeignConfig {
    
    @Bean
    public RequestInterceptor emptyHeaderInterceptor() {
        return template -> {
            log.info("👉 [FeignConfig] Before header modification - template.headers(): {}", template.headers());
            
            // 強制設置 header，不管原本有沒有
            template.header("test-header", Collections.singletonList(""));
            log.info("👉 [FeignConfig] After header modification - template.headers(): {}", template.headers());
        };
    }

    @Bean
    public Client feignClient() {
        return new CustomEmptyHeaderClient();
    }
} 
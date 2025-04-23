package com.example.feignconsumer;

import feign.Client;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request.Builder;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
public class CustomEmptyHeaderClient implements Client {

    private final OkHttpClient delegate;

    public CustomEmptyHeaderClient() {
        this.delegate = new OkHttpClient();
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        log.info("👉 [CustomEmptyHeaderClient] Starting request execution");
        log.info("👉 [CustomEmptyHeaderClient] Original request URL: {}", request.url());
        log.info("👉 [CustomEmptyHeaderClient] Original request headers: {}", request.headers());

        Builder builder = new Builder()
                .url(request.url());

        // 原有 headers 處理
        for (Map.Entry<String, Collection<String>> entry : request.headers().entrySet()) {
            String name = entry.getKey();
            for (String value : entry.getValue()) {
                // 強制加入空字串 header
                if (value == null || value.isEmpty()) {
                    builder.addHeader(name, "");
                    log.info("👉 [CustomEmptyHeaderClient] Adding empty header [{}: \"\"]", name);
                } else {
                    builder.addHeader(name, value);
                    log.info("👉 [CustomEmptyHeaderClient] Adding header [{}: {}]", name, value);
                }
            }
        }

        // 確保 test-header 存在
        if (!request.headers().containsKey("test-header")) {
            builder.addHeader("test-header", "");
            log.info("👉 [CustomEmptyHeaderClient] Adding missing test-header with empty value");
        }

        // 傳 body（僅對 POST/PUT 有效）
        RequestBody requestBody = null;
        if (request.body() != null) {
            requestBody = RequestBody.create(request.body(), MediaType.parse(request.headers().getOrDefault("Content-Type", List.of("application/json")).iterator().next()));
            log.info("👉 [CustomEmptyHeaderClient] Request body present, Content-Type: {}", request.headers().get("Content-Type"));
        }

        // 建立 OkHttp 的 Request
        okhttp3.Request okHttpRequest = builder
                .method(request.httpMethod().name(), requestBody)
                .build();

        log.info("👉 [CustomEmptyHeaderClient] Final OkHttp request headers: {}", okHttpRequest.headers());

        // 執行 request
        okhttp3.Response okHttpResponse = delegate.newCall(okHttpRequest).execute();

        log.info("👉 [CustomEmptyHeaderClient] Response status: {}", okHttpResponse.code());
        log.info("👉 [CustomEmptyHeaderClient] Response headers: {}", okHttpResponse.headers());

        // 回傳 Feign 格式的 Response
        return Response.builder()
                .status(okHttpResponse.code())
                .reason(okHttpResponse.message())
                .headers((Map<String, Collection<String>>) (Map<?, ?>) okHttpResponse.headers().toMultimap())
                .body(okHttpResponse.body() != null ? okHttpResponse.body().byteStream() : null, 
                      okHttpResponse.body() != null ? (int) okHttpResponse.body().contentLength() : null)
                .request(request)
                .build();
    }
} 
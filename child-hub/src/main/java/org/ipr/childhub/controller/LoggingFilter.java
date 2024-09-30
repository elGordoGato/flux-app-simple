package org.ipr.childhub.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Component
public class LoggingFilter implements WebFilter {
    private static final String REQUEST_ID = "requestId";

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        if (exchange.getRequest().getURI().getHost().equals("host.docker.internal")) {
            return chain.filter(exchange);
        }
        long startTime = Instant.now().toEpochMilli();
        ServerHttpRequest httpRequest = exchange.getRequest();
        String requestId = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID, requestId);
        log.info("Incoming request: method={}, path={}, params={}",
                httpRequest.getMethod(),
                httpRequest.getURI().getPath(),
                httpRequest.getQueryParams());
        return chain.filter(exchange).doAfterTerminate(() -> {
            long duration = System.currentTimeMillis() - startTime;
            log.info("Request completed:\n {}, Duration={}ms",
                    httpRequest.getURI().getQuery(),
                    duration);
        });
    }
}
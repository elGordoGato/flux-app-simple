package org.ipr.childhub.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.time.Instant;

@Slf4j
@Component
public class LoggingFilter implements WebFilter {

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        if (exchange.getRequest().getURI().getHost().equals("host.docker.internal")) {
            return chain.filter(exchange);
        }
        long startTime = Instant.now().toEpochMilli();
        ServerHttpRequest httpRequest = exchange.getRequest();
        String requestId = exchange.getLogPrefix();
        log.info("[{}] - Incoming request: method={}, path={}, params={}",
                requestId,
                httpRequest.getMethod(),
                httpRequest.getURI().getPath(),
                httpRequest.getQueryParams());
        return chain.filter(exchange).doAfterTerminate(() -> {
            long duration = System.currentTimeMillis() - startTime;
            log.info("[{}] - Request completed: Duration = {}ms",
                    requestId, duration);
        });
    }
}
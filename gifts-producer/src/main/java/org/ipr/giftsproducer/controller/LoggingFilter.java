package org.ipr.giftsproducer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest httpRequest = exchange.getRequest();
        log.info("Incoming request: method={}, uri={}, params={}, body=",
                httpRequest.getMethod(),
                httpRequest.getURI(),
                httpRequest.getQueryParams());
        //httpRequest.getBody().doOnEach(s -> log.info("{}", s)).subscribe();
        return chain.filter(exchange);
    }
}
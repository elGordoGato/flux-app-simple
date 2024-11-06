package org.ipr.childhub;

import reactor.core.publisher.Mono;

public class FluxPlayground {

    public static void main(String[] args) {
        Mono.just(2).doOnEach(System.out::println).subscribe();
    }

}

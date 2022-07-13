package com.xyzcorp.presenter;

import org.junit.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxTest {

    @Test
    public void testFlux() {
        Flux<Long> flux = Flux.create(longFluxSink -> {
            longFluxSink.next(10L);
            longFluxSink.next(20L);
            longFluxSink.next(30L);
            longFluxSink.next(93L);
            longFluxSink.next(17L);
            longFluxSink.next(22L);
            longFluxSink.next(43L);
            longFluxSink.next(131L);
            longFluxSink.next(39L);
            longFluxSink.complete();
        });

        Flux<Long> multiplyBy3 = flux
            .map(x -> x * 3);

        Disposable disposable = multiplyBy3
            .filter(x -> x % 2 == 0)
            .subscribe(System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("Complete"));

        Flux<String> emptyFlux = Flux.empty();
        Mono<String> stringMono =
            emptyFlux
                .map(String::valueOf)
                .reduce((s, s2) -> s + "," + s2);

        stringMono.subscribe(
            System.out::println,
            Throwable::printStackTrace,
            () -> System.out.println("Complete")); //This will only show

    }
}

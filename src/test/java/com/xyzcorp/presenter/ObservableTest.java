package com.xyzcorp.presenter;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;

import java.util.concurrent.Executors;


public class ObservableTest {

    @Test
    public void testBasicObservable() {
        Observable<Long> observable =
            Observable.create(emitter -> {
                emitter.onNext(10L);
                emitter.onNext(20L);
                emitter.onNext(31L);
                emitter.onNext(34L);
                emitter.onNext(35L);
                emitter.onNext(21L);
                emitter.onNext(17L);
                emitter.onNext(43L);
                emitter.onComplete();
            });

        Disposable disposable = observable
            .doOnNext(x -> debug("S0", x))
            .observeOn(Schedulers.computation())
            .doOnNext(x -> debug("S1", x)) //thread
            .map(x -> x * 3)
            .doOnNext(x -> debug("S2", x)) //thread
            .filter(x -> x % 2 == 0)
            .doOnNext(x -> debug("S3", x)) //thread
            .subscribeOn(Schedulers.newThread()) //I can place subscribeOn anywhere in the chain
            .observeOn(Schedulers.io())
            .subscribe(x -> debug("S4", x),
                Throwable::printStackTrace,
                () -> System.out.println("Done"));

    }

    private void debug(String marker, Long element) {
        System.out.printf("%s: %s [%s]\n", marker, element,
            Thread.currentThread());
    }
}

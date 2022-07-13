package com.xyzcorp.presenter;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import org.junit.Test;

public class FlowableTest {
    @Test
    public void testBasicFlowable() {

        Flowable<Long> flowable =
            Flowable.create(emitter -> {
                emitter.onNext(10L);
                emitter.onNext(20L);
                emitter.onNext(30L);
                emitter.onComplete();
            }, BackpressureStrategy.DROP);

    }
}

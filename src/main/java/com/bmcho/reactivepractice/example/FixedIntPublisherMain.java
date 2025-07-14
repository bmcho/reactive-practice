package com.bmcho.reactivepractice.example;

import lombok.SneakyThrows;

import java.util.concurrent.Flow;

public class FixedIntPublisherMain {

    @SneakyThrows
    public void run() {
        Flow.Publisher<FixedIntPublisher.Result> publisher = new FixedIntPublisher();
        Flow.Subscriber<FixedIntPublisher.Result> subscriber = new RequestNSubscriber<>(3);
        publisher.subscribe(subscriber);
        Thread.sleep(100);
    }
}
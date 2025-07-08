package com.bmcho.reacticestreampractice.coldpublisher;

import lombok.SneakyThrows;

public class SimpleColdPublisherMain {
    @SneakyThrows

    public void run() {
        // create publisher
        var publisher = new SimpleColdPublisher();

        // create subscriber1
        var subscriber = new SimpleNamedSubscriber<Integer>("subscriber1");
        publisher.subscribe(subscriber);

//        Thread.sleep(5000);

        // create subscriber2
        var subscriber2 = new SimpleNamedSubscriber<Integer>("subscriber2");
        publisher.subscribe(subscriber2);
    }
}

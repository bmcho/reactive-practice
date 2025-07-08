package com.bmcho.reacticestreampractice;

import com.bmcho.reacticestreampractice.coldpublisher.SimpleColdPublisher;
import com.bmcho.reacticestreampractice.coldpublisher.SimpleColdPublisherMain;
import com.bmcho.reacticestreampractice.coldpublisher.SimpleHotPublisherMain;
import com.bmcho.reacticestreampractice.coldpublisher.SimpleNamedSubscriber;
import com.bmcho.reacticestreampractice.example.FixedIntPublisher;
import com.bmcho.reacticestreampractice.example.FixedIntPublisherMain;
import com.bmcho.reacticestreampractice.example.RequestNSubscriber;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Flow;

@SpringBootApplication
public class ReacticestreamPracticeApplication {

    public static void main(String[] args) {

//        new FixedIntPublisherMain().run();

//        new SimpleColdPublisherMain().run();
//
        new SimpleHotPublisherMain().run();
    }

}

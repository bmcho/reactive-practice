package com.bmcho.reactivepractice;

import com.bmcho.reactivepractice.coldpublisher.SimpleHotPublisherMain;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReacticestreamPracticeApplication {

    public static void main(String[] args) {

//        new FixedIntPublisherMain().run();

//        new SimpleColdPublisherMain().run();
//
        new SimpleHotPublisherMain().run();
    }

}

package com.hendisantika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
 * https://www.linkedin.com/pulse/reactive-rest-services-spring-boot-aliaksandr-liakh
 * hendisantika@gmail.com
 * */

@SpringBootApplication
@RestController
public class ReactiveRestApplication {

    private final static int THREAD_COUNT = 100;

    private final static int DELAY_MILISECONDS = 100;

    private final ExecutorService executerService = Executors.newFixedThreadPool(THREAD_COUNT);

    private final Timer timer = new Timer();

    public static void main(String[] args) {
        SpringApplication.run(ReactiveRestApplication.class, args);
    }

    @RequestMapping("/sync")
    public String getAsync() throws Exception {
        Thread.sleep(DELAY_MILISECONDS);
        return "sync";
    }

    @RequestMapping(value = "/async-blocking")
    public DeferredResult<String> getAsyncBlocking() {
        DeferredResult<String> deferred = new DeferredResult<>();

        executerService.submit(() -> {
            try {
                Thread.sleep(DELAY_MILISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }

            deferred.setResult("async-blocking");

        });
        return deferred;
    }

    @RequestMapping("/async-nonblocking")
    public DeferredResult<String> getAsyncNonBlocking() {
        final DeferredResult<String> deferred = new DeferredResult<>();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (deferred.isSetOrExpired()) {
                    throw new RuntimeException();
                } else {
                    deferred.setResult("async-nonblocking");
                }
            }
        }, DELAY_MILISECONDS);
        return deferred;
    }
}

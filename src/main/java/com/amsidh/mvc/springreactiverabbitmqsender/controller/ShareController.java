package com.amsidh.mvc.springreactiverabbitmqsender.controller;

import com.amsidh.mvc.springreactiverabbitmqsender.model.Share;
import com.amsidh.mvc.springreactiverabbitmqsender.service.SendMessageToQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ShareController {
    private final SendMessageToQueue sendMessageToQueue;

    @PostMapping
    public String pushMessage(@RequestBody Share share) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mono<Void> voidMono = sendMessageToQueue.sendShareToQueue(share);
        voidMono.subscribe(result -> {
                    log.debug("Sent message to RabbitMQ");
                    countDownLatch.countDown();
                },
                throwable -> log.error("Got Error", throwable),
                () -> log.info("Share published successfully"));
        countDownLatch.await(1, TimeUnit.SECONDS);
        return "Share published successfully";
    }
}

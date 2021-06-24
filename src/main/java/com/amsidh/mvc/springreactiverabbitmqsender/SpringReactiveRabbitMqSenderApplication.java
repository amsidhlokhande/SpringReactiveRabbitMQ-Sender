package com.amsidh.mvc.springreactiverabbitmqsender;

import com.amsidh.mvc.springreactiverabbitmqsender.service.SendMessageToQueue;
import com.amsidh.mvc.springreactiverabbitmqsender.service.ShareService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Slf4j
@SpringBootApplication
public class SpringReactiveRabbitMqSenderApplication implements CommandLineRunner {
    private final ShareService shareService;
    private final SendMessageToQueue sendMessageToQueue;

    public static void main(String[] args) {
        SpringApplication.run(SpringReactiveRabbitMqSenderApplication.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(25);
        shareService.getShareStream(Duration.ofMillis(100))
                .take(25)
                .log("Share produced")
                .flatMap(sendMessageToQueue::sendShareToQueue)
                .subscribe(result -> {
                            log.debug("Sent message to RabbitMQ");
                            countDownLatch.countDown();
                        },
                        throwable -> log.error("Got Error", throwable),
                        () -> log.info("All Done"));
        countDownLatch.await(1, TimeUnit.SECONDS);

    }
}

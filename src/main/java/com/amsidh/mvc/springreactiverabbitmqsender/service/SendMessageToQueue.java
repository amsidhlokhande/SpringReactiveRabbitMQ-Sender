package com.amsidh.mvc.springreactiverabbitmqsender.service;

import com.amsidh.mvc.springreactiverabbitmqsender.model.Share;
import reactor.core.publisher.Mono;

public interface SendMessageToQueue {
    public Mono<Void> sendShareToQueue(Share share);
}

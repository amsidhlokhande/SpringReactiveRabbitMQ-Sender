package com.amsidh.mvc.springreactiverabbitmqsender.service.impl;

import com.amsidh.mvc.springreactiverabbitmqsender.config.AppConfig;
import com.amsidh.mvc.springreactiverabbitmqsender.model.Share;
import com.amsidh.mvc.springreactiverabbitmqsender.service.SendMessageToQueue;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendMessageToQueueImpl implements SendMessageToQueue {
    private final Sender sender;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Mono<Void> sendShareToQueue(Share share) {
        byte[] writeValueAsBytes = objectMapper.writeValueAsBytes(share);
        return sender.send(Mono.just(new OutboundMessage("", AppConfig.QUEUE_NAME, writeValueAsBytes)));
    }
}

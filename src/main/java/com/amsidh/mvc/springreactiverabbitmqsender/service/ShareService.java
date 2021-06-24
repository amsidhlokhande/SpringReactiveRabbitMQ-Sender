package com.amsidh.mvc.springreactiverabbitmqsender.service;

import com.amsidh.mvc.springreactiverabbitmqsender.model.Share;
import reactor.core.publisher.Flux;

import java.time.Duration;

public interface ShareService {
    Flux<Share> getShareStream(Duration duration);
}

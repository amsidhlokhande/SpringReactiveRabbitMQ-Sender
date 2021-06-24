package com.amsidh.mvc.springreactiverabbitmqsender.service.impl;

import com.amsidh.mvc.springreactiverabbitmqsender.model.Share;
import com.amsidh.mvc.springreactiverabbitmqsender.service.ShareService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ShareServiceImpl implements ShareService {

    private static final MathContext MATH_CONTEXT = new MathContext(2);
    private final List<Share> shares = new ArrayList<>();

    public ShareServiceImpl() {
        shares.add(new Share("State Bank Of India", 384.56));
        shares.add(new Share("ICICI Bank", 540.89));
        shares.add(new Share("Canara Bank", 765.76));
        shares.add(new Share("Alhabad Bank", 590.09));
        shares.add(new Share("Syndicate Bank", 534.69));
        shares.add(new Share("Mysure Bank", 586.87));
        shares.add(new Share("Andhra Bank", 352.50));
        shares.add(new Share("Citi Bank", 50.95));
        shares.add(new Share("HSBC Bank", 40.90));
        shares.add(new Share("Barclays Bank", 260.67));
        shares.add(new Share("Deutsche Bank", 586.83));
        shares.add(new Share("UBC Bank", 527.70));

    }


    @Override
    public Flux<Share> getShareStream(Duration duration) {
        return Flux.generate(() -> 0,
                (Integer index, SynchronousSink<Share> sink) -> {
                    Share updateShare = updateShare(this.shares.get(index));
                    sink.next(updateShare);
                    ++index;
                    return index % this.shares.size();
                })
                .zipWith(Flux.interval(duration))
                .map(Tuple2::getT1)
                .map(share -> {
                    share.setInstant(Instant.now());
                    return share;
                });
    }

    private Share updateShare(Share share) {
        BigDecimal priceChange = new BigDecimal(0.05 * new Random().nextDouble(), MATH_CONTEXT).multiply(share.getPrice());
        return new Share(share.getName(), share.getPrice().add(priceChange));
    }
}

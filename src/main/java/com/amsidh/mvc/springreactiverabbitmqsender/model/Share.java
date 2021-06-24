package com.amsidh.mvc.springreactiverabbitmqsender.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;

@Data
@NoArgsConstructor
public class Share implements Serializable {

    private static final MathContext MATH_CONTEXT = new MathContext(2);

    private String name;
    private BigDecimal price;
    private Instant instant;

    public Share(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Share(String name, Double price) {
        this.name = name;
        this.price = new BigDecimal(price, MATH_CONTEXT);
    }

}

package com.mysite.banking.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Amount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "amount_sequence")
    @SequenceGenerator(name = "amount_sequence", sequenceName = "hibernate_amount_seq", allocationSize = 1)
    private Integer id;
    private Currency currency;
    @Column(name = "amount_value")
    private BigDecimal value;
    @Version
    private  Long version;

    public Amount(Currency currency, BigDecimal value) {
        this.currency = currency;
        this.value = value;
    }
}

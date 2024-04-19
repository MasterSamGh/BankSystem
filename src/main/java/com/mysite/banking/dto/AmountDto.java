package com.mysite.banking.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class AmountDto implements Serializable {
    private Currency currency;
    private BigDecimal value;
}

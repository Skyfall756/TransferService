package ru.netology.moneytransferservice.repository;

import lombok.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Account {

    private long id;
    private String cardHolder;
    private String number;
    private String cvv;
    private String validTill;
    private BigDecimal balance;

    public void payment (BigDecimal amount){
        balance = balance.subtract(amount);
    }
    public void refill(BigDecimal amount){
        balance = balance.add(amount);
    }
}

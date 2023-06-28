package ru.netology.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Amount {

    private int value;
    private String currency;
}

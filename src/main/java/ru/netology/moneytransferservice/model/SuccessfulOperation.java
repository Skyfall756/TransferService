package ru.netology.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuccessfulOperation {
    private String operationId;
}

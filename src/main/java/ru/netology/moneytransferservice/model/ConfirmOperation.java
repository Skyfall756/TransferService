package ru.netology.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConfirmOperation {
    private String operationId;
    private String code;
}

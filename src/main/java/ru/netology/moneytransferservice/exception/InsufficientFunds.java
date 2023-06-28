package ru.netology.moneytransferservice.exception;

public class InsufficientFunds extends RuntimeException {

    private Integer id;
    public InsufficientFunds(String message, Integer id){
        super(message);
        this.id = id;

    }


}

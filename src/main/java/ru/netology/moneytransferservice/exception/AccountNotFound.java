package ru.netology.moneytransferservice.exception;

public class AccountNotFound extends RuntimeException{
    private Integer id;
    public AccountNotFound(String msg, Integer id){

        super(msg);
        this.id = id;
    }
}

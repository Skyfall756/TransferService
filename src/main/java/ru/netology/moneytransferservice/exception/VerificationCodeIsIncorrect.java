package ru.netology.moneytransferservice.exception;

public class VerificationCodeIsIncorrect extends RuntimeException{
    private int id;
     public VerificationCodeIsIncorrect (String msg, int id){
         super(msg);
         this.id = id;

     }
}

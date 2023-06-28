package ru.netology.moneytransferservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.moneytransferservice.exception.AccountNotFound;
import ru.netology.moneytransferservice.exception.InsufficientFunds;
import ru.netology.moneytransferservice.exception.VerificationCodeIsIncorrect;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(InsufficientFunds.class)
    public ResponseEntity<Exception> ifHandler (InsufficientFunds insFunds){
        return new ResponseEntity<>(insFunds, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFound.class)
    public ResponseEntity<RuntimeException> anfHandker (AccountNotFound accountNotFound) {
        return new ResponseEntity<>(accountNotFound, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(VerificationCodeIsIncorrect.class)
    public ResponseEntity<RuntimeException> vcii (VerificationCodeIsIncorrect vcii) {
        return new ResponseEntity<>(vcii, HttpStatus.BAD_REQUEST);
    }

}

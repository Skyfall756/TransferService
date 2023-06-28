package ru.netology.moneytransferservice.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.TransferDataObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class AccountRepositoryImplTest {

     AccountRepositoryImpl accountRepository;
     static Account account = new Account(1, "Vladimir", "0000000000000000",
             "123", "12/25", new BigDecimal("66000"));

    @BeforeEach
    public void newAccountRepository() {
        accountRepository = new AccountRepositoryImpl();

    }

    @AfterEach
    public void deleteAccountrepository() {
        accountRepository = null;
    }


    @ParameterizedTest
    @MethodSource("source")
    public void testTransfer(AtomicLong operationId, TransferDataObject tdo, int commission) {
        accountRepository.transfer(operationId, tdo, commission);
        Assertions.assertEquals(List.of(accountRepository.getAccount1().getBalance(),
                accountRepository.getAccount2().getBalance()), List.of(new BigDecimal(65890),
                new BigDecimal(700)));

    }

    public static Stream<Arguments> source() {
        return Stream.of(Arguments.of(new AtomicLong(2),
                new TransferDataObject("0000000000000000",
                        "12/25", "123", "1234567891234567",
                        new Amount(100, "RUB")), 10));
    }

    @ParameterizedTest
    @MethodSource("sourceForValidation")
    public void testValidation(AtomicLong operationId, TransferDataObject tdo, Account account) {
        Assertions.assertThrows(RuntimeException.class,
                () -> accountRepository.validation(operationId, tdo, account));

    }

    public static Stream<Arguments> sourceForValidation() {
        return Stream.of(Arguments.of(new AtomicLong(2),
                        new TransferDataObject("0000000000000000",
                        "12/26", "123", "1234567891234567",
                        new Amount(100, "RUB")), account),
                Arguments.of(new AtomicLong(2), new TransferDataObject("0000000000000000",
                        "12/25", "125", "1234567891234567",
                        new Amount(100, "RUB")), account),
                Arguments.of(new AtomicLong(2), new TransferDataObject("0000000000010000",
                        "12/25", "123", "1234567891234567",
                        new Amount(100, "RUB")),account));

    }
}

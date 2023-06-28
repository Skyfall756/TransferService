package ru.netology.moneytransferservice.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.TransferDataObject;
import ru.netology.moneytransferservice.repository.AccountRepositoryImpl;

import java.util.stream.Stream;

public class ServiceTest {

    TransferServiceImpl transferServiceImpl;

    @BeforeEach
    public void newTransferService() {
        transferServiceImpl = new TransferServiceImpl(new AccountRepositoryImpl());
    }

    @AfterEach
    public void deleteTransferService() {
        transferServiceImpl = null;
    }

    @Test
    public void testTransfer() {
        transferServiceImpl.transfer(new TransferDataObject("00000000000000000000",
                "12/25","123", "1234567891234567",
                new Amount(100, "RUB")));
        transferServiceImpl.transfer(new TransferDataObject("00000000000000000000",
                "12/25","123", "1234567891234567",
                new Amount(100, "RUB")));
        Assertions.assertTrue(transferServiceImpl.getOperationStorage().containsKey("2"));
    }

    @Test
    public void testConfirm() {
        transferServiceImpl.transfer(new TransferDataObject("0000000000000000",
                "12/25","123", "1234567891234567",
                new Amount(100, "RUB")));
        Assertions.assertEquals(transferServiceImpl.confirm("1", "0000"), "1");

    }

    @ParameterizedTest
    @MethodSource("source")
    public void testConfirmExceptions(String id, String code) {
        transferServiceImpl.transfer(new TransferDataObject("0000000000000000",
                "12/25","123", "1234567891234567",
                new Amount(100, "RUB")));
        Assertions.assertThrows(RuntimeException.class, ()-> transferServiceImpl.confirm(id, code));

    }

    public static Stream<Arguments> source() {
        return Stream.of(Arguments.of("99", "0000"),
                Arguments.of("1","0001"));
    }
}

package ru.netology.moneytransferservice.repository;


import org.junit.jupiter.api.*;

import java.math.BigDecimal;

public class AccountTest {
    Account account;

    @BeforeEach
    public void newAccount() {
        account = new Account(1, "Vladimir", "1111222233334444", "123",
                "12/25", new BigDecimal(10000));
    }
    @AfterEach
    public void deleteAccount() {
        account = null;
    }


    @Test
    public void testPayment(){
        account.payment(new BigDecimal(100));
        Assertions.assertEquals(account.getBalance(), new BigDecimal(9900));
    }

    @Test
    public void testRefill() {
        account.refill(new BigDecimal(100));
        Assertions.assertEquals(account.getBalance(), new BigDecimal(10100));
    }
}

package ru.netology.moneytransferservice.repository;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.exception.AccountNotFound;
import ru.netology.moneytransferservice.exception.InsufficientFunds;
import ru.netology.moneytransferservice.model.TransferDataObject;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Log4j
@Getter
@Repository
public class AccountRepositoryImpl implements AccountRepository{
    private Account account1 = new Account(1, "Vladimir", "0000000000000000",
            "123", "12/25", new BigDecimal("66000"));
    private Account account2 = new Account(2, "Oleg", "1234567891234567",
            "007", "05/24", new BigDecimal("600"));
    private Account account3 = new Account(3, "Irina", "1111 2222 3333 4444",
            "123", "12/25", new BigDecimal("25000"));

    private ConcurrentHashMap<String, Account> accountStorage;

    public AccountRepositoryImpl() {
        accountStorage = new ConcurrentHashMap<>();
        accountStorage.put(account1.getNumber(), account1);
        accountStorage.put(account2.getNumber(), account2);
        accountStorage.put(account3.getNumber(), account3);
    }


    public void transfer(AtomicLong operationId, TransferDataObject tdo, int commission) {

        if (accountStorage.containsKey(tdo.getCardFromNumber())
                && accountStorage.containsKey(tdo.getCardToNumber())) {

            Account accountFrom = accountStorage.get(tdo.getCardFromNumber());
            Account accountTo = accountStorage.get(tdo.getCardToNumber());

            validation(operationId, tdo, accountFrom);
            accountFrom.payment(new BigDecimal(tdo.getAmount().getValue() + commission));
            accountTo.refill(new BigDecimal(tdo.getAmount().getValue()));
        } else{
            log.error("Операция " + operationId+ ": карта не надена");
            throw new AccountNotFound("Операция " + operationId+ ": карта не надена",
                    operationId.intValue());
        }

    }

    public void validation(AtomicLong operationId, TransferDataObject tdo, Account account) {
        if (!tdo.getCardFromNumber().equals(account.getNumber())
                || !tdo.getCardFromCVV().equals(account.getCvv())
                || !tdo.getCardFromValidTill().equals(account.getValidTill())) {
            log.error("Операция " + operationId+ ": неверные CVV или срок действия");
            throw new AccountNotFound("Операция " + operationId+ ": неверные CVV или срок действия",
                    operationId.intValue());
        }
        if (account.getBalance().compareTo(new BigDecimal(tdo.getAmount().getValue())) < 0) {
            log.error("Операция " + operationId+ ": недостаточно средств на карте");
            throw new InsufficientFunds("Операция " + operationId+ ": недостаточно средств на карте",
                    operationId.intValue());
        }
    }
}

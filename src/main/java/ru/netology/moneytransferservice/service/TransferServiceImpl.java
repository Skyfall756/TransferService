package ru.netology.moneytransferservice.service;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exception.AccountNotFound;
import ru.netology.moneytransferservice.exception.VerificationCodeIsIncorrect;
import ru.netology.moneytransferservice.model.TransferDataObject;
import ru.netology.moneytransferservice.repository.AccountRepositoryImpl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Log4j
@Service
@Getter
public class TransferServiceImpl implements TransferService{

    private final AccountRepositoryImpl accountRepository;
    private ConcurrentHashMap<String, TransferDataObject> operationStorage;
    private AtomicLong operationId = new AtomicLong(0);
    private static final int COMMISSION = 10;
    private static String verificationCode = "0000";


    public TransferServiceImpl(AccountRepositoryImpl accountRepository) {

        this.accountRepository = accountRepository;
        this.operationStorage = new ConcurrentHashMap<>();
    }

    public String transfer(TransferDataObject tdo) {
        operationId.incrementAndGet();
        operationStorage.put(operationId.toString(), tdo);
        int com = tdo.getAmount().getValue() * COMMISSION/100;
        log.info("Операция с id=" + operationId + " сохранена, ждет подтверждения: "
                + tdo + ", комиссия = " + com);
        return operationId.toString();
    }

    public String confirm(String id, String code) {
        TransferDataObject tdo;
        if (operationStorage.containsKey(id)) {
            tdo = operationStorage.get(id);
            if (code.equals(verificationCode)) {
                int com = tdo.getAmount().getValue() * COMMISSION / 100;
                accountRepository.transfer(operationId, tdo, com);
                log.info("Проверка кода выполнена. Операция с id=" + operationId +
                        " успешно произведена: " + tdo + ", комиссия = " + com);
                return id;
            } else {
                log.error("Неверный код подтверждения для операции " + id);
                throw new VerificationCodeIsIncorrect("Неверный код подтверждения для операции "
                        + id, Integer.valueOf(id));
            }
        } else {
            log.error("Операция с id=" + id + " не найдена");
            throw new AccountNotFound("Операция с id=" + id + " не найдена", Integer.valueOf(id));
        }
    }
}

package ru.netology.moneytransferservice.repository;

import ru.netology.moneytransferservice.model.TransferDataObject;

import java.util.concurrent.atomic.AtomicLong;

public interface AccountRepository {

    void transfer (AtomicLong operationId, TransferDataObject tdo, int commission);

    void validation (AtomicLong operationId, TransferDataObject tdo, Account account);
}

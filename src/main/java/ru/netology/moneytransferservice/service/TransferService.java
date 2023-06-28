package ru.netology.moneytransferservice.service;

import ru.netology.moneytransferservice.model.TransferDataObject;

public interface TransferService {

    String transfer(TransferDataObject tdo);
    String confirm(String id, String code);

}

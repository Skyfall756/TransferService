package ru.netology.moneytransferservice.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.moneytransferservice.model.ConfirmOperation;
import ru.netology.moneytransferservice.model.SuccessfulOperation;
import ru.netology.moneytransferservice.model.TransferDataObject;
import ru.netology.moneytransferservice.service.TransferServiceImpl;

@RestController
@CrossOrigin(origins = "${origins.host}")
public class TransferController {

    private TransferServiceImpl transferServiceImpl;

    public TransferController(TransferServiceImpl transferServiceImpl) {
        this.transferServiceImpl = transferServiceImpl;
    }

    @PostMapping("transfer")
    public SuccessfulOperation transfer(@RequestBody TransferDataObject tdo) {
        return new SuccessfulOperation(transferServiceImpl.transfer(tdo));

    }


    @PostMapping("confirmOperation")
    public SuccessfulOperation confirmOperation (@RequestBody ConfirmOperation confirmOperation) {
        return new SuccessfulOperation(transferServiceImpl.confirm(confirmOperation.getOperationId(),
                confirmOperation.getCode()));
    }



}

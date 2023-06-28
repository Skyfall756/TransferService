package ru.netology.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransferDataObject {


    private String cardFromNumber;
    private String cardFromValidTill;
    private String cardFromCVV;
    private String cardToNumber;
    private Amount amount;

    @Override
    public String toString() {
        return "Перевод с карты (" + cardFromNumber + ", " + cardFromValidTill + ", " + cardFromCVV
                + ") на карту (" + cardToNumber + "), сумма = " + amount.getValue();
    }


}

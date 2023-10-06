package uz.pdp.atm.exception;

import uz.pdp.atm.enums.CardType;

import java.util.Set;

public class CardIsNotSupportedException extends RuntimeException {
    public CardIsNotSupportedException(Set<CardType> atmCardTypes, CardType cardType) {
        super(String.format("ATM does not support %s Card. Only supports %s Card(s)", cardType,
                String.join(", ", atmCardTypes.stream().map(Enum::toString).toList())));
    }
}

package uz.pdp.atm.enums;

import lombok.Getter;

import java.util.Currency;

@Getter
public enum CardType {
    HUMO(Currency.getInstance(CurrencyType.UZS.name())),
    UZCARD(Currency.getInstance(CurrencyType.UZS.name())),
    VISA(Currency.getInstance(CurrencyType.USD.name()));

    private final Currency currency;

    CardType(Currency currency) {
        this.currency = currency;
    }
}

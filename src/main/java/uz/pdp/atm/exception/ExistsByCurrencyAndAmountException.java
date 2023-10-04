package uz.pdp.atm.exception;

public class ExistsByCurrencyAndAmountException extends RuntimeException {
    public ExistsByCurrencyAndAmountException(String className, String currency, Integer amount) {
        super(String.format("%s by currency '%s' and amount '%d' already exists", className, currency, amount));
    }
}

package uz.pdp.atm.exception;

public class NotFoundByAmountException extends RuntimeException {
    public NotFoundByAmountException(String className, Integer amount) {
        super(String.format("%s by amount '%d' not found", className, amount));
    }
}

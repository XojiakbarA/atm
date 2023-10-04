package uz.pdp.atm.exception;

public class NotFoundByNumberException extends RuntimeException {
    public NotFoundByNumberException(String className, String number) {
        super(String.format("%s by number '%s' not found", className, number));
    }
}

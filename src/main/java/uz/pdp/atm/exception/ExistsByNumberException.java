package uz.pdp.atm.exception;

public class ExistsByNumberException extends RuntimeException {
    public ExistsByNumberException(String className, String number) {
        super(String.format("%s by number '%s' already exists", className, number));
    }
}

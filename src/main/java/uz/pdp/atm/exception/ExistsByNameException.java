package uz.pdp.atm.exception;

public class ExistsByNameException extends RuntimeException {
    public ExistsByNameException(String className, String name) {
        super(String.format("%s by name '%s' already exists", className, name));
    }
}

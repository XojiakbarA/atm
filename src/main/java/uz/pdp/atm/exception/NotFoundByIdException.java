package uz.pdp.atm.exception;

public class NotFoundByIdException extends RuntimeException {
    public NotFoundByIdException(String className, Long id) {
        super(String.format("%s by id '%d' not found", className, id));
    }
}

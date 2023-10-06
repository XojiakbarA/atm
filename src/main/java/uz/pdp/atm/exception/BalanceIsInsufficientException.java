package uz.pdp.atm.exception;

public class BalanceIsInsufficientException extends RuntimeException {
    public BalanceIsInsufficientException(String className, Double balance) {
        super(String.format("%s balance is insufficient. Balance - %f", className, balance));
    }

    public BalanceIsInsufficientException(String className, Long balance) {
        super(String.format("%s balance is insufficient. Balance - %d", className, balance));
    }
}

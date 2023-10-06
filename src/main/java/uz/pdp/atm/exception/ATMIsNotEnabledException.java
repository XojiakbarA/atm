package uz.pdp.atm.exception;

public class ATMIsNotEnabledException extends RuntimeException {
    public ATMIsNotEnabledException() {
        super("ATM is not enabled");
    }
}

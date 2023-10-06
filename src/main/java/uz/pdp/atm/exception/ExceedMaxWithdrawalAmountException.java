package uz.pdp.atm.exception;

public class ExceedMaxWithdrawalAmountException extends RuntimeException {
    public ExceedMaxWithdrawalAmountException(Long maxWithdrawalAmount) {
        super(String.format("Amount exceeded the max withdrawal amount. Max withdrawal amount  - %d", maxWithdrawalAmount));
    }
}

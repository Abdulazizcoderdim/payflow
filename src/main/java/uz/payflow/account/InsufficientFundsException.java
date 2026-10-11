package uz.payflow.account;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String accountNumber) {
        super("Hisobda mablag' yetarli emas: " + accountNumber);
    }
}

package uz.payflow.transfer;

public class TransferRejectedException extends RuntimeException {

    public TransferRejectedException(String message) {
        super(message);
    }
}

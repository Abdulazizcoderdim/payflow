package uz.payflow.transfer;

import java.math.BigDecimal;
import java.time.Instant;

import uz.payflow.account.Currency;

public record TransferResponse(
    Long id,
    Long fromAccountId,
    Long toAccountId,
    BigDecimal amount,
    Currency currency,
    Instant createdAt) {

    public static TransferResponse from(Transfer transfer) {
        return new TransferResponse(
            transfer.getId(),
            transfer.getFromAccountId(),
            transfer.getToAccountId(),
            transfer.getAmount(),
            transfer.getCurrency(),
            transfer.getCreatedAt());
    }
}

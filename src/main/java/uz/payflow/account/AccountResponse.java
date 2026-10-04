package uz.payflow.account;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountResponse(
    Long id,
    String number,
    String ownerName,
    Currency currency,
    BigDecimal balance,
    Instant createdAt
) {
    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getNumber(),
                account.getOwnerName(),
                account.getCurrency(),
                account.getBalance(),
                account.getCreatedAt());
    }
}

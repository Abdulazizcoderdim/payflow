package uz.payflow.transfer;


import jakarta.persistence.*;
import uz.payflow.account.Currency;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 64)
    private String idempotencyKey;

    @Column(name = "from_account_id", nullable = false)
    private Long fromAccountId;

    @Column(name = "to_account_id", nullable = false)
    private Long toAccountId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private Currency currency;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected Transfer() {
    }

    public Transfer(String idempotencyKey, Long fromAccountId, Long toAccountId,
                    BigDecimal amount, Currency currency) {
        this.idempotencyKey = idempotencyKey;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.currency = currency;
        this.createdAt = Instant.now();
    }

    public boolean isSameRequest(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        return this.fromAccountId.equals(fromAccountId)
                && this.toAccountId.equals(toAccountId)
                && this.amount.compareTo(amount) == 0;
    }

    public Long getId() { return id; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public Long getFromAccountId() { return fromAccountId; }
    public Long getToAccountId() { return toAccountId; }
    public BigDecimal getAmount() { return amount; }
    public Currency getCurrency() { return currency; }
    public Instant getCreatedAt() { return createdAt; }
}

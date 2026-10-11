package uz.payflow.transfer;

import java.lang.StackWalker.Option;
import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import uz.payflow.account.Account;
import uz.payflow.account.AccountNotFoundException;
import uz.payflow.account.AccountRepository;

@Service
public class TransferService {

    private static final int MAX_KEY_LENGTH = 64;

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    public TransferService(AccountRepository accountRepository, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    @Transactional
    public Transfer transfer(String idempotencyKey, Long fromId, Long toId, BigDecimal amount) {
        if(idempotencyKey.isBlank() || idempotencyKey.length() > MAX_KEY_LENGTH) {
            throw new TransferRejectedException("Idempotency-Key 1 dan ^4 belgigacha bo'lishi kerak");
        }
        if (fromId.equals(toId)) {
            throw new TransferRejectedException("Hisobdan o'sha hisobning o'ziga o'tkazib bo'lmaydi");
        }

        Account first = lockAccount(Math.min(fromId, toId));
        Account second = lockAccount(Math.max(fromId, toId));
        Account from = first.getId().equals(fromId) ? first : second;
        Account to = from == first ? second : first;

        Optional<Transfer> existing = transferRepository.findByIdempotencyKey(idempotencyKey);
        if(existing.isPresent()) {
            if(!existing.get().isSameRequest(fromId, toId, amount)) {
                throw new TransferRejectedException("Idempotency-Key boshqa so'rov uchun ishlatilgan");
            }
            return existing.get();
        }

        if(from.getCurrency() != to.getCurrency()) {
            throw new TransferRejectedException("Hisoblar valyutasi har xil");
        }

        from.debit(amount);
        to.credit(amount);
        return transferRepository.save(new Transfer(idempotencyKey, fromId, toId, amount, from.getCurrency()));
    }

    private Account lockAccount(Long id) {
        return accountRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }
}

package uz.payflow.account;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private static final String BALANCE_ACCOUNT = "20208";

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account create(String ownerName, Currency currency) {
        String number = generateNumber(currency);
        return accountRepository.save(new Account(number, ownerName, currency));
    }

    @Transactional(readOnly = true)
    public Account getById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    private String generateNumber(Currency currency) {
        long sequence = accountRepository.nextNumberSequence();
        return BALANCE_ACCOUNT + currency.getNumericCode() + String.format("%012d", sequence);
    }
}

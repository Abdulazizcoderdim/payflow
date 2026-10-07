package uz.payflow.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountServiceTest {

    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void createGeneratesNumberFromCurrencyAndSequence() {
        when(accountRepository.nextNumberSequence()).thenReturn(42L);
        when(accountRepository.save(any(Account.class))).thenAnswer(call -> call.getArgument(0));

        Account account = accountService.create("Ali Valiyev", Currency.USD);

        assertThat(account.getNumber()).isEqualTo("20208840000000000042");
        assertThat(account.getNumber()).hasSize(20);
        assertThat(account.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void getByIdThrowsWhenAccountDoesNotExist() {
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getById(999L))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage("Hisob topilmadi: id=999");
    }
}

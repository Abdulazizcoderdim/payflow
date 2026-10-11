package uz.payflow.account;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@Valid @RequestBody CreateAccountRequest request) {
        Account account = accountService.create(request.ownerName(), request.currency());
        return  AccountResponse.from(account);
    }

    @PostMapping("/{id}/deposits")
    public AccountResponse deposit(@PathVariable Long id, @Valid @RequestBody DepositRequest request) {
        return AccountResponse.from(accountService.deposit(id, request.amount()));
    }

    @GetMapping("/{id}")
    public AccountResponse getById(@PathVariable Long id) {
        return AccountResponse.from(accountService.getById(id));
    }
}

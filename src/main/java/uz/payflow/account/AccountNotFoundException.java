package uz.payflow.account;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long id) {
        super("Hisob topilmadi: id=" + id);
    }
}

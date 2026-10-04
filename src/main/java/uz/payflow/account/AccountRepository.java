package uz.payflow.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT nextval('account_number_seq')", nativeQuery = true)
    long nextNumberSequence();
}

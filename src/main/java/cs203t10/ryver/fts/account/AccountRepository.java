package cs203t10.ryver.fts.account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
  List<Account> findByCustomerId(Integer customerId);
  Optional<Account> findByAccountId(Integer accountId);
}

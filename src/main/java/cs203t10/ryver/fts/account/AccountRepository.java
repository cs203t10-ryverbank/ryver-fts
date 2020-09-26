package cs203t10.ryver.fts.account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
  List<Account> findByUserId(long userId);
  Optional<Account> findByAccountId(long accountId);
}

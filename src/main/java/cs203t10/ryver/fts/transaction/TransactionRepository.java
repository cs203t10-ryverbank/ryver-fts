package cs203t10.ryver.fts.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
  List<Transaction> findBySenderId(long userId);
  List<Transaction> findbyReceiverId(long userId);
}

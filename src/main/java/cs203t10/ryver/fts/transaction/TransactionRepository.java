package cs203t10.ryver.fts.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
  List<Transaction> findBySenderId(Integer senderId);
  List<Transaction> findByReceiverId(Integer receiverId);
}

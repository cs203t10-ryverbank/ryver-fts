package cs203t10.ryver.fts.transaction;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TransactionService {
  private TransactionRepository transactionRepository;

  @Autowired
  public TransactionService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public Transaction addTransaction(Transaction transaction) {
    // Acccess account service to update bank balances.
    return transactionRepository.save(transaction);
  }

  public List<Transaction> findByReceiverId(Long id) {
    return transactionRepository.findByReceiverId(id);
  }

  public List<Transaction> findBySenderId(Long id) {
    return transactionRepository.findBySenderId(id);
  }

  public List<Transaction> findById(long id) {
    List<Transaction> transactionsById = new ArrayList<Transaction>(findByReceiverId(id));
    transactionsById.addAll(findBySenderId(id));
    return transactionsById;
  }
}

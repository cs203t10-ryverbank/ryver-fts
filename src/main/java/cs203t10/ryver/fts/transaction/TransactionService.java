package cs203t10.ryver.fts.transaction;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import cs203t10.ryver.fts.account.AccountService;

@Service
public class TransactionService {
  private TransactionRepository transactionRepository;
  private AccountService accountService;

  @Autowired
  public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
    this.transactionRepository = transactionRepository;
    this.accountService = accountService;
  }

  public Transaction addTransaction(Transaction transaction) {
    double amount = transaction.getAmount();
    Long receiverId = transaction.getReceiverId();
    Long senderId = transaction.getSenderId();

    accountService.addToAccountBalance(receiverId, amount);
    accountService.deductFromAccountBalance(senderId, amount);

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

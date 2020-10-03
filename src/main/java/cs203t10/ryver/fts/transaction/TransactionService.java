package cs203t10.ryver.fts.transaction;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import cs203t10.ryver.fts.account.AccountService;
import cs203t10.ryver.fts.account.Account;

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
    Double amount = transaction.getAmount();
    Account receiver = transaction.getReceiverAccountId();
    Account sender = transaction.getSenderAccountId();

    accountService.deductFromAccountBalance(sender, amount);
    accountService.addToAccountBalance(receiver, amount);

    Transaction savedTransaction = transactionRepository.save(transaction.toBuilder().build());
    return savedTransaction;
  }

  public List<Transaction> findByReceiverAccountId(Integer id) {
    return transactionRepository.findByReceiverAccountId(id);
  }

  public List<Transaction> findBySenderAccountId(Integer id) {
    return transactionRepository.findBySenderAccountId(id);
  }

  public List<Transaction> findById(Integer id) {
    List<Transaction> transactionsById = new ArrayList<Transaction>(findByReceiverAccountId(id));
    transactionsById.addAll(findBySenderAccountId(id));
    return transactionsById;
  }

}

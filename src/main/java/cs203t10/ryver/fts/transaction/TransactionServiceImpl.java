package cs203t10.ryver.fts.transaction;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import cs203t10.ryver.fts.account.AccountService;
import cs203t10.ryver.fts.transaction.view.TransactionInfo;
import cs203t10.ryver.fts.account.Account;

import static cs203t10.ryver.fts.transaction.TransactionException.TransactionNotFoundException;

@Service
public class TransactionServiceImpl implements TransactionService {
  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountService accountService;

  public Transaction findById(Integer id) {
    return transactionRepository.findById(id)
            .orElseThrow(() -> new TransactionNotFoundException(id));
  }

  public Transaction addTransaction(Integer senderAccountId, Integer receiverAccountId, Double amount) {
    accountService.deductFromAvailableBalance(senderAccountId, amount);
    Account senderAccount = accountService.deductFromBalance(senderAccountId, amount);
    accountService.addToAvailableBalance(receiverAccountId, amount);
    Account receiverAccount = accountService.addToBalance(receiverAccountId, amount);

    Transaction transaction = Transaction.builder()
                                  .senderAccount(senderAccount)
                                  .receiverAccount(receiverAccount)
                                  .amount(amount)
                                  .status("accepted")
                                  .build();
    return transactionRepository.save(transaction);
  }

  public List<Transaction> findBySenderAccountId(Integer id) {
    return transactionRepository.findBySenderAccountIdAndStatus(id, "accepted");
  }

  public Transaction addPendingTransaction(Integer senderAccountId, Double amount) {
    Account senderAccount = accountService.deductFromAvailableBalance(senderAccountId, amount);
    Transaction pendingTransaction = Transaction.builder()
                                  .senderAccount(senderAccount)
                                  .amount(amount)
                                  .status("pending")
                                  .build();
    return transactionRepository.save(pendingTransaction);
  }

  public Transaction acceptPendingTransaction(Integer id, Integer receiverAccountId) {
    Transaction pendingTransaction = transactionRepository.findById(id)
                                      .orElseThrow(() -> new TransactionNotFoundException(id));

    Integer senderAccountId = pendingTransaction.getSenderAccount().getId();
    Double amount = pendingTransaction.getAmount();
    Account senderAccount = accountService.deductFromBalance(senderAccountId, amount);
    accountService.addToAvailableBalance(receiverAccountId, amount);
    Account receiverAccount = accountService.addToBalance(receiverAccountId, amount);

    pendingTransaction.setReceiverAccount(receiverAccount);
    pendingTransaction.setStatus("accepted");
    return transactionRepository.save(pendingTransaction);
  }

  public Transaction rejectPendingTransaction(Integer id) {
    Transaction pendingTransaction = transactionRepository.findById(id)
                                      .orElseThrow(() -> new TransactionNotFoundException(id));
    Integer senderAccountId = pendingTransaction.getSenderAccount().getId();
    Double amount = pendingTransaction.getAmount();
    Account senderAccount = accountService.addToAvailableBalance(senderAccountId, amount);
    pendingTransaction.setStatus("rejected");
    return transactionRepository.save(pendingTransaction);
  }

}